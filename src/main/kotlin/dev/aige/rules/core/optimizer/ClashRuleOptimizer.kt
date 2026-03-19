package dev.aige.rules.core.optimizer

import dev.aige.rules.core.entities.ClashRule
import java.math.BigInteger

/**
 * Clash 规则优化器
 *
 * 执行两类去冗操作：
 * 1. 域名去冗（Domain Deduplication）
 *    - 若 DOMAIN-SUFFIX,a.b.com 与 DOMAIN-SUFFIX,b.com 同时存在，前者冗余
 *    - 若 DOMAIN,api.example.com 被 DOMAIN-SUFFIX,example.com 覆盖，DOMAIN 规则冗余
 *
 * 2. IP-CIDR 聚合（CIDR Aggregation）
 *    - 将相邻/重叠的 IPv4 CIDR 块合并为最少数量的更大 CIDR 块
 *    - 同样处理 IPv6 CIDR
 */
object ClashRuleOptimizer {

    fun optimize(rules: Set<ClashRule>): Set<ClashRule> {
        val result = rules.toMutableSet()
        deduplicateDomains(result)
        // 若存在 GEOIP,CN,no-resolve，则所有 IP-CIDR/6 规则均被其覆盖，可安全移除
        if (result.any { it.type == ClashRule.Type.GEO_IP && it.argument == "CN" && it.isDNSResolve }) {
            result.removeAll(result.filter {
                it.type == ClashRule.Type.IP_CIDR || it.type == ClashRule.Type.IP_CIDR6
            }.toSet())
        } else {
            aggregateIpCidr(result)
            aggregateIpCidr6(result)
        }
        return result
    }

    // ────────────────────────────────────────────────────────────────
    // 域名去冗
    // ────────────────────────────────────────────────────────────────

    private fun deduplicateDomains(rules: MutableSet<ClashRule>) {
        // 收集所有 DOMAIN-SUFFIX 的参数，用于 O(1) 查找
        val suffixSet: HashSet<String> = rules
            .filter { it.type == ClashRule.Type.DOMAIN_SUFFIX }
            .mapTo(HashSet()) { it.argument }

        val toRemove = mutableSetOf<ClashRule>()

        for (rule in rules) {
            when (rule.type) {
                ClashRule.Type.DOMAIN_SUFFIX -> {
                    // 若其任意上级 domain 也在 suffixSet 中，则冗余
                    // 例：api.openai.com → 检查 openai.com, com
                    if (hasParentSuffixInSet(rule.argument, suffixSet)) {
                        toRemove += rule
                    }
                }

                ClashRule.Type.DOMAIN -> {
                    // 若其自身或任意上级 domain 在 suffixSet 中，则冗余
                    // 例：api.openai.com → 检查 api.openai.com, openai.com, com
                    if (hasSuffixCoverage(rule.argument, suffixSet)) {
                        toRemove += rule
                    }
                }

                else -> {} // 其他类型规则不处理
            }
        }

        rules.removeAll(toRemove)
    }

    /**
     * 检查 domain 是否有上级 suffix 存在于 set 中（不含自身），用于 DOMAIN-SUFFIX 去重
     * "api.openai.com" → 检查 "openai.com"、"com"
     */
    private fun hasParentSuffixInSet(domain: String, set: HashSet<String>): Boolean {
        val parts = domain.split(".")
        // 从第 1 层父级开始（跳过自身）
        for (i in 1 until parts.size - 1) {
            if (set.contains(parts.drop(i).joinToString("."))) return true
        }
        return false
    }

    /**
     * 检查 domain 自身或其上级是否被 DOMAIN-SUFFIX 覆盖，用于 DOMAIN 去重
     * "api.openai.com" → 检查 "api.openai.com"、"openai.com"、"com"
     */
    private fun hasSuffixCoverage(domain: String, set: HashSet<String>): Boolean {
        val parts = domain.split(".")
        for (i in 0 until parts.size - 1) {
            if (set.contains(parts.drop(i).joinToString("."))) return true
        }
        return false
    }

    // ────────────────────────────────────────────────────────────────
    // IPv4 CIDR 聚合
    // ────────────────────────────────────────────────────────────────

    private fun aggregateIpCidr(rules: MutableSet<ClashRule>) {
        val cidrRules = rules.filter { it.type == ClashRule.Type.IP_CIDR }
        if (cidrRules.size < 2) return

        // 保留 no-resolve 与非 no-resolve 两组分别聚合
        val noResolveGroup = cidrRules.filter { it.isDNSResolve }
        val resolveGroup = cidrRules.filter { !it.isDNSResolve }

        rules.removeAll(cidrRules.toSet())
        rules.addAll(aggregateIpv4Group(noResolveGroup, isDNSResolve = true))
        rules.addAll(aggregateIpv4Group(resolveGroup, isDNSResolve = false))
    }

    private fun aggregateIpv4Group(cidrRules: List<ClashRule>, isDNSResolve: Boolean): List<ClashRule> {
        if (cidrRules.isEmpty()) return emptyList()

        // 解析为 [start, end] Long 范围
        val ranges = cidrRules.mapNotNull { parseIpv4Cidr(it.argument) }
        val merged = mergeRanges(ranges)

        // 将合并后的范围转换回 CIDR 规则
        return merged.flatMap { (start, end) ->
            rangeToCidrs4(start, end).map { cidr ->
                ClashRule(ClashRule.Type.IP_CIDR, cidr, isDNSResolve)
            }
        }
    }

    /** 解析 "a.b.c.d/prefix" → Pair(startLong, endLong)，失败返回 null */
    private fun parseIpv4Cidr(cidr: String): Pair<Long, Long>? {
        return try {
            val (ipStr, prefixStr) = cidr.split("/")
            val prefix = prefixStr.toInt()
            val ipLong = ipStr.split(".").fold(0L) { acc, part -> (acc shl 8) or part.toLong() }
            val mask = if (prefix == 0) 0L else (-1L shl (32 - prefix)) and 0xFFFFFFFFL
            val start = ipLong and mask
            val end = start or (mask.inv() and 0xFFFFFFFFL)
            Pair(start, end)
        } catch (_: Exception) {
            null
        }
    }

    /** 将 [start, end] Long 转为最优 CIDR 列表（IPv4） */
    private fun rangeToCidrs4(start: Long, end: Long): List<String> {
        val result = mutableListOf<String>()
        var cur = start
        while (cur <= end) {
            // 找到从 cur 开始能取的最大对齐块
            var prefix = 32
            while (prefix > 0) {
                val blockSize = 1L shl (32 - (prefix - 1))
                val alignedStart = (cur ushr (32 - (prefix - 1))) shl (32 - (prefix - 1))
                if (alignedStart == cur && cur + blockSize - 1 <= end) {
                    prefix--
                } else break
            }
            val blockSize = 1L shl (32 - prefix)
            result.add(longToIpv4(cur) + "/$prefix")
            cur += blockSize
            if (cur > 0xFFFFFFFFL) break
        }
        return result
    }

    private fun longToIpv4(ip: Long): String =
        "${(ip shr 24) and 0xFF}.${(ip shr 16) and 0xFF}.${(ip shr 8) and 0xFF}.${ip and 0xFF}"

    // ────────────────────────────────────────────────────────────────
    // IPv6 CIDR 聚合
    // ────────────────────────────────────────────────────────────────

    private fun aggregateIpCidr6(rules: MutableSet<ClashRule>) {
        val cidrRules = rules.filter { it.type == ClashRule.Type.IP_CIDR6 }
        if (cidrRules.size < 2) return

        val noResolveGroup = cidrRules.filter { it.isDNSResolve }
        val resolveGroup = cidrRules.filter { !it.isDNSResolve }

        rules.removeAll(cidrRules.toSet())
        rules.addAll(aggregateIpv6Group(noResolveGroup, isDNSResolve = true))
        rules.addAll(aggregateIpv6Group(resolveGroup, isDNSResolve = false))
    }

    private fun aggregateIpv6Group(cidrRules: List<ClashRule>, isDNSResolve: Boolean): List<ClashRule> {
        if (cidrRules.isEmpty()) return emptyList()

        val ranges = cidrRules.mapNotNull { parseIpv6Cidr(it.argument) }
        val merged = mergeRangesBig(ranges)

        return merged.flatMap { (start, end) ->
            rangeToCidrs6(start, end).map { cidr ->
                ClashRule(ClashRule.Type.IP_CIDR6, cidr, isDNSResolve)
            }
        }
    }

    /** 解析 IPv6 CIDR → Pair(BigInteger start, BigInteger end)，失败返回 null */
    private fun parseIpv6Cidr(cidr: String): Pair<BigInteger, BigInteger>? {
        return try {
            val slashIdx = cidr.lastIndexOf('/')
            val ipStr = cidr.substring(0, slashIdx)
            val prefix = cidr.substring(slashIdx + 1).toInt()
            val ipBytes = expandIpv6(ipStr) ?: return null
            val ipBig = BigInteger(1, ipBytes)
            val totalBits = 128
            val maskBig = if (prefix == 0) BigInteger.ZERO
            else BigInteger.ONE.shiftLeft(totalBits - prefix).subtract(BigInteger.ONE).not()
                .and(BigInteger.ONE.shiftLeft(totalBits).subtract(BigInteger.ONE))
            val start = ipBig.and(maskBig)
            val end = start.or(maskBig.not().and(BigInteger.ONE.shiftLeft(totalBits).subtract(BigInteger.ONE)))
            Pair(start, end)
        } catch (_: Exception) {
            null
        }
    }

    /** 展开 IPv6 地址为 16 字节数组 */
    private fun expandIpv6(ip: String): ByteArray? {
        return try {
            val normalized = ip.lowercase()
            val parts = normalized.split("::")
            val bytes = ByteArray(16)
            if (parts.size == 2) {
                val left = if (parts[0].isEmpty()) emptyList() else parts[0].split(":")
                val right = if (parts[1].isEmpty()) emptyList() else parts[1].split(":")
                val missing = 8 - left.size - right.size
                val all = left + List(missing) { "0" } + right
                all.forEachIndexed { i, s ->
                    val v = s.toInt(16)
                    bytes[i * 2] = (v shr 8).toByte()
                    bytes[i * 2 + 1] = v.toByte()
                }
            } else {
                val groups = normalized.split(":")
                groups.forEachIndexed { i, s ->
                    val v = s.toInt(16)
                    bytes[i * 2] = (v shr 8).toByte()
                    bytes[i * 2 + 1] = v.toByte()
                }
            }
            bytes
        } catch (_: Exception) {
            null
        }
    }

    private fun rangeToCidrs6(start: BigInteger, end: BigInteger): List<String> {
        val result = mutableListOf<String>()
        var cur = start
        val max = BigInteger.ONE.shiftLeft(128).subtract(BigInteger.ONE)
        while (cur <= end) {
            var prefix = 128
            while (prefix > 0) {
                val blockSize = BigInteger.ONE.shiftLeft(128 - (prefix - 1))
                val alignedStart = cur.shiftRight(128 - (prefix - 1)).shiftLeft(128 - (prefix - 1))
                if (alignedStart == cur && cur.add(blockSize).subtract(BigInteger.ONE) <= end) {
                    prefix--
                } else break
            }
            val blockSize = BigInteger.ONE.shiftLeft(128 - prefix)
            result.add(bigIntToIpv6(cur) + "/$prefix")
            cur = cur.add(blockSize)
            if (cur > max) break
        }
        return result
    }

    private fun bigIntToIpv6(ip: BigInteger): String {
        val bytes = ip.toByteArray()
        val padded = ByteArray(16)
        val offset = 16 - bytes.size.coerceAtMost(16)
        bytes.copyInto(padded, offset.coerceAtLeast(0), (bytes.size - 16).coerceAtLeast(0))
        val groups = (0 until 8).map { i ->
            ((padded[i * 2].toInt() and 0xFF shl 8) or (padded[i * 2 + 1].toInt() and 0xFF)).toString(16)
        }
        // 简单输出，不压缩 :: （够用）
        return groups.joinToString(":")
    }

    // ────────────────────────────────────────────────────────────────
    // 通用区间合并
    // ────────────────────────────────────────────────────────────────

    /** 合并 Long 区间列表 */
    private fun mergeRanges(ranges: List<Pair<Long, Long>>): List<Pair<Long, Long>> {
        if (ranges.isEmpty()) return emptyList()
        val sorted = ranges.sortedBy { it.first }
        val merged = mutableListOf<Pair<Long, Long>>()
        var (curStart, curEnd) = sorted[0]
        for (i in 1 until sorted.size) {
            val (s, e) = sorted[i]
            if (s <= curEnd + 1) {
                curEnd = maxOf(curEnd, e)
            } else {
                merged += Pair(curStart, curEnd)
                curStart = s; curEnd = e
            }
        }
        merged += Pair(curStart, curEnd)
        return merged
    }

    /** 合并 BigInteger 区间列表（用于 IPv6） */
    private fun mergeRangesBig(ranges: List<Pair<BigInteger, BigInteger>>): List<Pair<BigInteger, BigInteger>> {
        if (ranges.isEmpty()) return emptyList()
        val sorted = ranges.sortedWith(compareBy { it.first })
        val merged = mutableListOf<Pair<BigInteger, BigInteger>>()
        var (curStart, curEnd) = sorted[0]
        for (i in 1 until sorted.size) {
            val (s, e) = sorted[i]
            if (s <= curEnd.add(BigInteger.ONE)) {
                curEnd = if (e > curEnd) e else curEnd
            } else {
                merged += Pair(curStart, curEnd)
                curStart = s; curEnd = e
            }
        }
        merged += Pair(curStart, curEnd)
        return merged
    }
}
