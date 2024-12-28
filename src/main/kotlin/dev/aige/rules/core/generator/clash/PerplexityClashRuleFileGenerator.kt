package dev.aige.rules.core.generator.clash

import dev.aige.rules.core.entities.ClashRule

class PerplexityClashRuleFileGenerator : ClashRuleFileGenerator("perplexity.list") {
    override suspend fun generate() = write { rules: MutableSet<ClashRule> ->
        // 自定义配置
        rules.add(ClashRule(ClashRule.Type.DOMAIN_KEYWORD, "perplexity", false))
        rules.add(ClashRule(ClashRule.Type.DOMAIN_SUFFIX, "pplx.ai", false))
    }
}