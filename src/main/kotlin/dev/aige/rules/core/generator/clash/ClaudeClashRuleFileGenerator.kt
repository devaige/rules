package dev.aige.rules.core.generator.clash

import dev.aige.rules.core.entities.ClashRule
import dev.aige.rules.provider.blackmatrix.entities.BlackMatrixFile

class ClaudeClashRuleFileGenerator : ClashRuleFileGenerator("Claude.list") {
    override suspend fun generate() = write { rules: MutableSet<ClashRule> ->
        // 读取 BlackMatrix 配置文件
        val claudeBlackMatrixFile = BlackMatrixFile("Claude/Claude.list")
        rules.addAll(claudeBlackMatrixFile.rules)
        val anthropicBlackMatrixFile = BlackMatrixFile("Anthropic/Anthropic.list")
        rules.addAll(anthropicBlackMatrixFile.rules)
    }
}