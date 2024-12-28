package dev.aige.rules.core.generator.clash

import dev.aige.rules.core.entities.ClashRule
import dev.aige.rules.provider.blackmatrix.entities.BlackMatrixFile

class LineClashRuleFileGenerator : ClashRuleFileGenerator("Line.list") {
    override suspend fun generate() = write { rules: MutableSet<ClashRule> ->
        // 读取 BlackMatrix 配置文件
        val lineBlackMatrixFile = BlackMatrixFile("Line/Line.list")
        rules.addAll(lineBlackMatrixFile.rules)
        val lineTVBlackMatrixFile = BlackMatrixFile("LineTV/LineTV.list")
        rules.addAll(lineTVBlackMatrixFile.rules)
    }
}