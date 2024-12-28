package dev.aige.rules.core.generator.clash

import dev.aige.rules.core.entities.ClashRule
import dev.aige.rules.provider.blackmatrix.entities.BlackMatrixFile

class NetflixClashRuleFileGenerator : ClashRuleFileGenerator("Netflix.list") {
    override suspend fun generate() = write { rules: MutableSet<ClashRule> ->
        // 读取 BlackMatrix 配置文件
        val netflixBlackMatrixFile = BlackMatrixFile("Netflix/Netflix.list")
        rules.addAll(netflixBlackMatrixFile.rules)
    }
}