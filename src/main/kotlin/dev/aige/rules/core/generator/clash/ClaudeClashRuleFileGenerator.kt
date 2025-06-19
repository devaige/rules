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
        // 添加一些自定义的规则
        // 参考：https://gist.github.com/sarices/017da597ae6b28063bbdd52693d78385
        rules.add(ClashRule(ClashRule.Type.DOMAIN_SUFFIX, "claudeusercontent.com"))
        // 参考：https://github.com/szkane/ClashRuleSet/blob/36a41f03fe1fa5f806053c0248db0c95da003a51/Clash/Ruleset/AiDomain.list#L19
        rules.add(ClashRule(ClashRule.Type.PROCESS_NAME, "Claude"))
        rules.add(ClashRule(ClashRule.Type.PROCESS_NAME, "Claude Helper"))
        rules.add(ClashRule(ClashRule.Type.DOMAIN_KEYWORD, "claude"))
        rules.add(ClashRule(ClashRule.Type.DOMAIN_SUFFIX, "usefathom.com"))
    }
}