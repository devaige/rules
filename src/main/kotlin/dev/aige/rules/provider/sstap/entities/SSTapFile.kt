package dev.aige.rules.provider.sstap.entities

import java.io.File
import java.nio.file.Paths

class SSTapFile(private val filename: String) {
    private companion object {
        private val FOLDER: File = Paths.get("sstap/rules").toAbsolutePath().toFile()
    }

    private val lines: List<String> by lazy { File(FOLDER, filename).readLines() }
    val conf: SSTapConfig
        get() {
            val parts: List<String> = lines.first().trim().removePrefix("#").split(",")
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
    val rules: Set<SSTapRule>
        get() {
            val rules: MutableSet<SSTapRule> = mutableSetOf()
            lines.forEachIndexed { index: Int, str: String ->
                if (index == 0) return@forEachIndexed
                val line: String = str.trim()
                if (line.isNotEmpty()) rules.add(SSTapRule(line))
            }
            return rules
        }
}