package dev.aige.rules.core.generator.clash

import dev.aige.rules.core.entities.ClashRule
import dev.aige.rules.provider.acl4ssr.entities.ACL4SSRFile
import dev.aige.rules.provider.blackmatrix.entities.BlackMatrixFile
import dev.aige.rules.provider.blackmatrix.entities.BlackMatrixPath

class OpenAIClashRuleFileGenerator : ClashRuleFileGenerator("OpenAI.list") {
    private companion object {
        private val skip: Set<ClashRule> = setOf(
            ClashRule(ClashRule.Type.DOMAIN, "gemini.google.com"), // 不知道为什么把 Gemini 加进来了可能是为了省事
            ClashRule(ClashRule.Type.DOMAIN_SUFFIX, "stripe.com"), // Stripe 绑定后会自动扣款，后续就不需要跟随 OpenAI 了
        )
    }

    private val blackMatrixFilePaths: Set<BlackMatrixPath> = setOf(
        BlackMatrixPath("OpenAI"),
    )
    private val acL4SSRFiles: Set<ACL4SSRFile> = setOf(
        ACL4SSRFile("OpenAi"),
    )

    override suspend fun generate() = write { rules: MutableSet<ClashRule> ->
        // 读取 BlackMatrix 配置文件
        rules.addAll(blackMatrixFilePaths.flatMap { BlackMatrixFile(it.path).rules - skip })
        // 读取 ACL4SSR 配置文件
        rules.addAll(acL4SSRFiles.flatMap { it.rules - skip })
    }
}