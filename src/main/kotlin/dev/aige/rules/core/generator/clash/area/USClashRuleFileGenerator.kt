package dev.aige.rules.core.generator.clash.area

import dev.aige.rules.core.entities.ClashRule
import dev.aige.rules.core.generator.clash.ClashAreaRuleFileGenerator

class USClashRuleFileGenerator : ClashAreaRuleFileGenerator("us.list") {
    override suspend fun generate() = write { rules: MutableSet<ClashRule> ->
        rules.add(ClashRule(ClashRule.Type.DOMAIN_SUFFIX, "redpocket.com"))
    }
}