package dev.aige.rules.core.generator.clash

import dev.aige.rules.core.entities.ClashRule
import dev.aige.rules.provider.blackmatrix.entities.BlackMatrixFile

class OpenAIClashRuleFileGenerator : ClashRuleFileGenerator("OpenAI.list") {
    private companion object {
        private val skip: Set<ClashRule> = setOf(
            ClashRule(ClashRule.Type.DOMAIN, "gemini.google.com"), // 不知道为什么把 Gemini 加进来了可能是为了省事
            ClashRule(ClashRule.Type.DOMAIN_SUFFIX, "stripe.com"), // Stripe 绑定后会自动扣款，后续就不需要跟随 OpenAI 了
        )
    }

    override suspend fun generate() = write { rules: MutableSet<ClashRule> ->
        // 读取 BlackMatrix 配置文件
        val openAIBlackMatrixFile = BlackMatrixFile("OpenAI/OpenAI.list")
        rules.addAll(openAIBlackMatrixFile.rules - skip)
    }
}