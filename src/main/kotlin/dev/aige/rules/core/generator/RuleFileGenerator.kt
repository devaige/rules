package dev.aige.rules.core.generator

import dev.aige.rules.core.entities.Rule
import java.io.File
import java.io.PrintWriter

abstract class RuleFileGenerator<T : Rule>(protected val filename: String) {
    protected abstract val output: File
    abstract suspend fun generate()

    /**
     * 子类可重写此方法对规则集做后处理（去冗、聚合等），默认不做任何处理。
     */
    protected open fun postProcess(rules: MutableSet<T>): Set<T> = rules

    protected suspend fun write(block: suspend (MutableSet<T>) -> Unit) {
        val pw: PrintWriter = output.printWriter()
        val rules: MutableSet<T> = mutableSetOf()
        block.invoke(rules)
        val processed = postProcess(rules)
        processed.forEach { pw.println(it.format()) }
        pw.flush()
        pw.close()
    }
}