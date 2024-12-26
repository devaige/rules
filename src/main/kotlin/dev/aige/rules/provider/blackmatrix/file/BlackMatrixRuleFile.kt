package dev.aige.rules.provider.blackmatrix.file

import dev.aige.rules.provider.BaseRuleFile
import java.io.File

open class BlackMatrixRuleFile(file: File) : BaseRuleFile(file) {
    protected val rules: Set<String>
        get() = lines.dropWhile { it.trim().startsWith("#") }.map { it.trim() }.filter { it.isNotEmpty() }.toSet()
}