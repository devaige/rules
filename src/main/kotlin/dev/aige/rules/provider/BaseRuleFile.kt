package dev.aige.rules.provider

import java.io.File

abstract class BaseRuleFile(private val file: File) {
    protected val lines: List<String> by lazy { file.readLines() }
}