package dev.aige.rules.core.generator.clash

import dev.aige.rules.core.entities.ClashRule
import dev.aige.rules.provider.blackmatrix.entities.BlackMatrixFile

class MicrosoftClashRuleFileGenerator : ClashRuleFileGenerator("Microsoft.list") {
    override suspend fun generate() = write { rules: MutableSet<ClashRule> ->
        // 读取 BlackMatrix 配置文件
        val oneDriveBlackMatrixFile = BlackMatrixFile("OneDrive/OneDrive.list")
        rules.addAll(oneDriveBlackMatrixFile.rules)
        val microsoftBlackMatrixFile = BlackMatrixFile("Microsoft/Microsoft.list")
        rules.addAll(microsoftBlackMatrixFile.rules)
        val teamsBlackMatrixFile = BlackMatrixFile("Teams/Teams.list")
        rules.addAll(teamsBlackMatrixFile.rules)
        val microsoftEdgeBlackMatrixFile = BlackMatrixFile("MicrosoftEdge/MicrosoftEdge.list")
        rules.addAll(microsoftEdgeBlackMatrixFile.rules)
        val bingBlackMatrixFile = BlackMatrixFile("Bing/Bing.list")
        rules.addAll(bingBlackMatrixFile.rules)
        val copilotBlackMatrixFile = BlackMatrixFile("Copilot/Copilot.list")
        rules.addAll(copilotBlackMatrixFile.rules)
    }
}