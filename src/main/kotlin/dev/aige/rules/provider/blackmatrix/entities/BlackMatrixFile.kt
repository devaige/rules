package dev.aige.rules.provider.blackmatrix.entities

import dev.aige.rules.core.entities.ClashRule
import java.io.File
import java.nio.file.Paths

data class BlackMatrixFile(private val filename: String) {
    private companion object {
        private val FOLDER: File = Paths.get("blackmatrix/rule/Clash").toAbsolutePath().toFile()
    }

    private val lines: List<String> by lazy { File(FOLDER, filename).readLines() }
    val rules: Set<ClashRule>
        get() {
            val rules: MutableSet<ClashRule> = mutableSetOf()
            lines.forEach { str: String ->
                val line: String = str.trim()
                if (line.startsWith("#")) return@forEach
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