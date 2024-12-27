package dev.aige.rules.core.generator

import dev.aige.rules.core.entities.Rule
import java.io.File
import java.io.PrintWriter

abstract class RuleFileGenerator<T : Rule>(protected val filename: String) {
    protected abstract val output: File
    abstract suspend fun generate()
    protected inline fun write(block: (MutableSet<T>) -> Unit) {
        val pw: PrintWriter = output.printWriter()
        val rules: MutableSet<T> = mutableSetOf()
        block.invoke(rules)
        rules.forEach { pw.println(it.format()) }
        pw.flush()
        pw.close()
    }
}