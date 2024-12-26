package dev.aige.rules.provider.blackmatrix.file

import dev.aige.rules.adapter.ClashRuleTranslator
import dev.aige.rules.core.ClashRule
import java.io.File

class BlackMatrixClashRuleFile(file: File) : BlackMatrixRuleFile(file), ClashRuleTranslator {
    override suspend fun translate(): List<ClashRule> = rules.map { rule: String ->
        println("处理 BlackMatrix 规则：$rule")
        val parts: List<String> = rule.split(",")
        ClashRule(
            ClashRule.Type.from(parts[0]),
            parts[1],
            if (parts.size > 2) parts[2] == ClashRule.DNS_RESOLVE else false,
        )
    }
}