package dev.aige.rules

import dev.aige.rules.exporter.Exporter
import dev.aige.rules.exporter.impls.EARuleExporter
import dev.aige.rules.exporter.impls.OpenAIRuleExporter
import java.io.File
import java.nio.file.Paths

private const val OUTPUT: String = "mine"

private val exporters: List<Exporter> = listOf(
    OpenAIRuleExporter(),
    EARuleExporter(),
)

suspend fun main() {
    val output: File = Paths.get(OUTPUT).toAbsolutePath().toFile()
    println("输出目录：${output}")
    if (output.exists() && output.isDirectory) {
        output.deleteRecursively()
        println("删除规则目录：${output}")
    }
    output.mkdirs()
    exporters.forEach { it.export(output) }
}