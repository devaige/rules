package dev.aige.rules.core.generator.clash

import dev.aige.rules.core.entities.ClashRule
import dev.aige.rules.provider.blackmatrix.entities.BlackMatrixFile

class TikTokClashRuleFileGenerator : ClashRuleFileGenerator("TikTok.list") {
    override suspend fun generate() = write { rules: MutableSet<ClashRule> ->
        // 读取 BlackMatrix 配置文件
        val tiktokBlackMatrixFile = BlackMatrixFile("TikTok/TikTok.list")
        rules.addAll(tiktokBlackMatrixFile.rules)
    }
}