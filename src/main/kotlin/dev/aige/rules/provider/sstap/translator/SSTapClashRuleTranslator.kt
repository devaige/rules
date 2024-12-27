package dev.aige.rules.provider.sstap.translator

import dev.aige.rules.core.entities.ClashRule
import dev.aige.rules.core.translator.ClashRuleTranslator
import dev.aige.rules.provider.sstap.entities.SSTapConfig
import dev.aige.rules.provider.sstap.entities.SSTapRule

class SSTapClashRuleTranslator(
    config: SSTapConfig
) : AbstractSSTapRuleTranslator(config), ClashRuleTranslator<SSTapRule> {
    override suspend fun translate(input: SSTapRule): ClashRule =
        ClashRule(ClashRule.Type.IP_CIDR, input.cidr, config.isDNSResolve)
}