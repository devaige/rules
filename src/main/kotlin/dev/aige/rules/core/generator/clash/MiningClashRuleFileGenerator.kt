package dev.aige.rules.core.generator.clash

import dev.aige.rules.core.entities.ClashRule

// 加密货币挖矿相关的规则生成器
class MiningClashRuleFileGenerator : ClashRuleFileGenerator("Mining.list") {
    override suspend fun generate() = write { rules: MutableSet<ClashRule> ->
        // https://nexus.xyz/
        rules.add(ClashRule(ClashRule.Type.DOMAIN, "nexus.xyz"))
        rules.add(ClashRule(ClashRule.Type.DOMAIN_SUFFIX, "nexus.xyz"))
    }
}