package dev.aige.rules.core.generator.clash

import dev.aige.rules.core.entities.ClashRule
import dev.aige.rules.provider.blackmatrix.entities.BlackMatrixFile

class GoogleClashRuleFileGenerator : ClashRuleFileGenerator("Google.list") {
    override suspend fun generate() = write { rules: MutableSet<ClashRule> ->
        // 读取 BlackMatrix 配置文件
        val geminiBlackMatrixFile = BlackMatrixFile("Gemini/Gemini.list")
        rules.addAll(geminiBlackMatrixFile.rules)
        val bardAIBlackMatrixFile = BlackMatrixFile("BardAI/BardAI.list")
        rules.addAll(bardAIBlackMatrixFile.rules)
        val googleBlackMatrixFile = BlackMatrixFile("Google/Google.list")
        rules.addAll(googleBlackMatrixFile.rules)
        val googleDriveBlackMatrixFile = BlackMatrixFile("GoogleDrive/GoogleDrive.list")
        rules.addAll(googleDriveBlackMatrixFile.rules)
        val googleEarthBlackMatrixFile = BlackMatrixFile("GoogleEarth/GoogleEarth.list")
        rules.addAll(googleEarthBlackMatrixFile.rules)
        val googleFCMBlackMatrixFile = BlackMatrixFile("GoogleFCM/GoogleFCM.list")
        rules.addAll(googleFCMBlackMatrixFile.rules)
        val googleSearchBlackMatrixFile = BlackMatrixFile("GoogleSearch/GoogleSearch.list")
        rules.addAll(googleSearchBlackMatrixFile.rules)
        val googleVoiceBlackMatrixFile = BlackMatrixFile("GoogleVoice/GoogleVoice.list")
        rules.addAll(googleVoiceBlackMatrixFile.rules)
        val youTubeBlackMatrixFile = BlackMatrixFile("YouTube/YouTube.list")
        rules.addAll(youTubeBlackMatrixFile.rules)
        val youTubeMusicBlackMatrixFile = BlackMatrixFile("YouTubeMusic/YouTubeMusic.list")
        rules.addAll(youTubeMusicBlackMatrixFile.rules)
        val chromecastBlackMatrixFile = BlackMatrixFile("Chromecast/Chromecast.list")
        rules.addAll(chromecastBlackMatrixFile.rules)
    }
}