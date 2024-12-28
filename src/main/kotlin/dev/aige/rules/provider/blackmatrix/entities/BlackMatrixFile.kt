package dev.aige.rules.provider.blackmatrix.entities

import dev.aige.rules.core.entities.ClashRule
import java.io.File
import java.nio.file.Paths

data class BlackMatrixFile(private val filepath: String) {
    private companion object {
        private val FOLDER: File = Paths.get("blackmatrix/rule/Clash").toAbsolutePath().toFile()
    }

    private val lines: List<String> by lazy { File(FOLDER, filepath).readLines() }
    val rules: Set<ClashRule>
        get() {
            val rules: MutableSet<ClashRule> = mutableSetOf()
            lines.forEach { str: String ->
                val line: String = str.trim()
                if (line.startsWith("#")) return@forEach
                val parts: List<String> = line.split(",")

                val type: ClashRule.Type = ClashRule.Type.from(parts[0])
                val condition: String = parts[1]
                // BlackMatrix 的 STUN 这个规则下的 IPv6 规则好像有问题，直接忽略吧先
                if (filepath == "STUN/STUN.list" && type == ClashRule.Type.IP_CIDR6) return@forEach
                rules.add(
                    ClashRule(
                        type, condition,
                        if (parts.size > 2) parts[2].equals(ClashRule.DNS_RESOLVE, true) else false,
                    )
                )
            }
            return rules
        }
}