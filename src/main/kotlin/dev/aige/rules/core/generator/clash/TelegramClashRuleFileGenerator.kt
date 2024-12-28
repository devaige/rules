package dev.aige.rules.core.generator.clash

import dev.aige.rules.core.entities.ClashRule
import dev.aige.rules.provider.blackmatrix.entities.BlackMatrixFile

class TelegramClashRuleFileGenerator : ClashRuleFileGenerator("Telegram.list") {
    override suspend fun generate() = write { rules: MutableSet<ClashRule> ->
        // 读取 BlackMatrix 配置文件
        val telegramBlackMatrixFile = BlackMatrixFile("Telegram/Telegram.list")
        rules.addAll(telegramBlackMatrixFile.rules)
        val telegramNLBlackMatrixFile = BlackMatrixFile("TelegramNL/TelegramNL.list")
        rules.addAll(telegramNLBlackMatrixFile.rules)
        val telegramSGBlackMatrixFile = BlackMatrixFile("TelegramSG/TelegramSG.list")
        rules.addAll(telegramSGBlackMatrixFile.rules)
        val telegramUSBlackMatrixFile = BlackMatrixFile("TelegramUS/TelegramUS.list")
        rules.addAll(telegramUSBlackMatrixFile.rules)
    }
}