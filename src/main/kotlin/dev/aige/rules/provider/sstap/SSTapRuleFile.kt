package dev.aige.rules.provider.sstap

import dev.aige.rules.adapter.ClashRuleTranslator
import dev.aige.rules.core.ClashRule
import java.io.File

class SSTapRuleFile(private val file: File) : ClashRuleTranslator {
    private val lines: List<String> by lazy { file.readLines() }
    private val conf: SSTapConfig
        get() {
            val parts: List<String> = lines.first().trim().removePrefix("#").split(",").map { it.trim() }
            return SSTapConfig(
                parts[0],
                parts[1],
                SSTapConfig.ProxyType.from(parts[2].toInt()),
                SSTapConfig.ProxyType.from(parts[3].toInt()),
                parts[4].toInt(), parts[5].toInt(),
                parts[6].toInt() == 0,
                SSTapConfig.DNSProxyType.from(parts[7].toInt()),
                parts[8],
            )
        }
    private val ips: Set<String>
        get() = lines.drop(1).map { it.trim() }.filter { it.isNotEmpty() }.toSet()

    override suspend fun translate(): List<ClashRule> =
        ips.map { ip: String -> ClashRule(ClashRule.Type.IP_CIDR, ip, conf.isDNSResolve) }
}