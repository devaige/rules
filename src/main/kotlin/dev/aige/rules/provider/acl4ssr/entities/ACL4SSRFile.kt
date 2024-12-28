package dev.aige.rules.provider.acl4ssr.entities

import dev.aige.rules.core.entities.ClashRule
import dev.aige.rules.provider.acl4ssr.ACL4SSR
import java.io.File

data class ACL4SSRFile(val filename: String) {
    private val lines: List<String> by lazy { File(ACL4SSR.rules, "$filename.list").readLines() }
    val rules: Set<ClashRule>
        get() {
            val rules: MutableSet<ClashRule> = mutableSetOf()
            lines.forEach { str: String ->
                val line: String = str.trim()
                if (line.isEmpty() || line.startsWith("#")) return@forEach
                val parts: List<String> = line.split(",")
                rules.add(
                    ClashRule(
                        ClashRule.Type.from(parts[0]),
                        parts[1],
                        if (parts.size > 2) parts[2].equals(ClashRule.DNS_RESOLVE, true) else false,
                    )
                )
            }
            return rules
        }
}