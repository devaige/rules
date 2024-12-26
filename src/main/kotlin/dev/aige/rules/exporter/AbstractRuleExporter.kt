package dev.aige.rules.exporter

import dev.aige.rules.adapter.ClashRuleTranslator
import dev.aige.rules.core.ClashRule
import java.io.File
import java.io.PrintWriter

abstract class AbstractRuleExporter(private val filename: String) : Exporter {
    private val translators: MutableSet<ClashRuleTranslator> = mutableSetOf()
    protected fun register(translator: ClashRuleTranslator) = translators.add(translator)

    override suspend fun export(folder: File) {
        val rules: List<ClashRule> = translators.flatMap { it.translate() }
        val pw: PrintWriter = File(folder, filename).printWriter()
        rules.forEach { pw.println(it.toString()) }
        pw.flush()
        pw.close()
    }
}