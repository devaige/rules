package dev.aige.rules.core.generator.clash

import dev.aige.rules.core.entities.ClashRule
import dev.aige.rules.provider.blackmatrix.addBlackMatrixFileRules
import dev.aige.rules.provider.blackmatrix.entities.BlackMatrixPath

class RejectClashRuleFileGenerator : ClashRuleFileGenerator("Reject.list") {
    private val blackMatrixFilePrefix: Set<BlackMatrixPath> = setOf(
        BlackMatrixPath("AdvertisingLite"),
        BlackMatrixPath("Advertising"),
        BlackMatrixPath("AdvertisingTest"),
        BlackMatrixPath("Direct"),
        BlackMatrixPath("ZhihuAds"),
        BlackMatrixPath("AdvertisingMiTV"),
        BlackMatrixPath("AdvertisingLite"),
        BlackMatrixPath("Advertising"),
        BlackMatrixPath("AdvertisingTest"),
        BlackMatrixPath("Hijacking"),
        BlackMatrixPath("Privacy"),
        BlackMatrixPath("ZhihuAds"),
        BlackMatrixPath("AdGuardSDNSFilter"),
        BlackMatrixPath("EasyPrivacy"),
        BlackMatrixPath("BlockHttpDNS"),
    )

    override suspend fun generate() = write { rules: MutableSet<ClashRule> ->
        // 读取 BlackMatrix 配置文件
        blackMatrixFilePrefix.forEach { rules.addBlackMatrixFileRules(it) }
    }
}