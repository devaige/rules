package dev.aige.rules.core.generator.clash

import dev.aige.rules.core.entities.ClashRule

class GrokClashRuleFileGenerator : ClashRuleFileGenerator("Grok.list") {
    override suspend fun generate() = write { rules: MutableSet<ClashRule> ->
        // 添加一些自定义的规则
        // 参考：https://github.com/szkane/ClashRuleSet/blob/36a41f03fe1fa5f806053c0248db0c95da003a51/Clash/Ruleset/AiDomain.list#L37
        rules.add(ClashRule(ClashRule.Type.PROCESS_NAME, "grok"))
        rules.add(ClashRule(ClashRule.Type.DOMAIN_KEYWORD, "grok"))
        rules.add(ClashRule(ClashRule.Type.DOMAIN_SUFFIX, "x.ai"))
        rules.add(ClashRule(ClashRule.Type.DOMAIN_SUFFIX, "grok.com"))
    }
}