package dev.aige.rules.core.generator.clash

import dev.aige.rules.OUTPUT
import java.io.File
import java.nio.file.Paths

abstract class ClashAreaRuleFileGenerator(filename: String) : ClashRuleFileGenerator(filename) {
    private val folder: File
        get() {
            val folder: File = Paths.get("$OUTPUT/clash/area").toAbsolutePath().toFile()
            if (!folder.exists() || !folder.isDirectory) folder.mkdirs()
            return folder
        }
    override val output: File
        get() = File(folder, filename)
}