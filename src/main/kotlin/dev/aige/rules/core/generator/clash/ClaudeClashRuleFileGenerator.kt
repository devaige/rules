package dev.aige.rules.core.generator.clash

import dev.aige.rules.core.entities.ClashRule
import dev.aige.rules.provider.acl4ssr.entities.ACL4SSRFile
import dev.aige.rules.provider.blackmatrix.entities.BlackMatrixFile
import dev.aige.rules.provider.blackmatrix.entities.BlackMatrixPath

class ClaudeClashRuleFileGenerator : ClashRuleFileGenerator("Claude.list") {
    private val blackMatrixFilePaths: Set<BlackMatrixPath> = setOf(
        BlackMatrixPath("Claude"),
        BlackMatrixPath("Anthropic"),
    )
    private val acL4SSRFiles: Set<ACL4SSRFile> = setOf(
        ACL4SSRFile("Claude"),
        ACL4SSRFile("ClaudeAI"),
    )

    override suspend fun generate() = write { rules: MutableSet<ClashRule> ->
        // 读取 BlackMatrix 配置文件
        rules.addAll(blackMatrixFilePaths.flatMap { BlackMatrixFile(it.path).rules })
        // 读取 ACL4SSR 配置文件
        rules.addAll(acL4SSRFiles.flatMap { it.rules })
    }
}