package dev.aige.rules.core.generator.clash

import dev.aige.rules.OUTPUT
import dev.aige.rules.core.entities.ClashRule
import dev.aige.rules.core.generator.RuleFileGenerator
import java.io.File
import java.nio.file.Paths

abstract class ClashRuleFileGenerator(filename: String) : RuleFileGenerator<ClashRule>(filename) {
    private val folder: File
        get() {
            val folder: File = Paths.get("$OUTPUT/clash").toAbsolutePath().toFile()
            if (!folder.exists() || !folder.isDirectory) folder.mkdirs()
            return folder
        }
    override val output: File
        get() = File(folder, filename)
}