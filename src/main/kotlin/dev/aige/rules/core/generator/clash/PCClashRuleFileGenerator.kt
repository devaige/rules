package dev.aige.rules.core.generator.clash

import dev.aige.rules.core.entities.ClashRule

class PCClashRuleFileGenerator : ClashRuleFileGenerator("PC.list") {
    override suspend fun generate() = write { rules: MutableSet<ClashRule> ->
        // 读取 BlackMatrix 配置文件
        rules.add(ClashRule(ClashRule.Type.DOMAIN_SUFFIX, "pincong.rocks"))
        rules.add(ClashRule(ClashRule.Type.DOMAIN_KEYWORD, "pincong"))
    }
}