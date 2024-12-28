package dev.aige.rules.core.generator.clash

import dev.aige.rules.core.entities.ClashRule
import dev.aige.rules.provider.blackmatrix.entities.BlackMatrixFile

class AppleClashRuleFileGenerator : ClashRuleFileGenerator("Apple.list") {
    override suspend fun generate() = write { rules: MutableSet<ClashRule> ->
        // 读取 BlackMatrix 配置文件
        val appleBlackMatrixFile = BlackMatrixFile("Apple/Apple.list")
        rules.addAll(appleBlackMatrixFile.rules)
        val appleDevBlackMatrixFile = BlackMatrixFile("AppleDev/AppleDev.list")
        rules.addAll(appleDevBlackMatrixFile.rules)
        val appleFirmwareBlackMatrixFile = BlackMatrixFile("AppleFirmware/AppleFirmware.list")
        rules.addAll(appleFirmwareBlackMatrixFile.rules)
        val appleHardwareFirmwareBlackMatrixFile = BlackMatrixFile("AppleHardware/AppleHardware.list")
        rules.addAll(appleHardwareFirmwareBlackMatrixFile.rules)
        val appleIDBlackMatrixFile = BlackMatrixFile("AppleID/AppleID.list")
        rules.addAll(appleIDBlackMatrixFile.rules)
        val appleMailBlackMatrixFile = BlackMatrixFile("AppleMail/AppleMail.list")
        rules.addAll(appleMailBlackMatrixFile.rules)
        val appleMediaBlackMatrixFile = BlackMatrixFile("AppleMedia/AppleMedia.list")
        rules.addAll(appleMediaBlackMatrixFile.rules)
        val appleMusicBlackMatrixFile = BlackMatrixFile("AppleMusic/AppleMusic.list")
        rules.addAll(appleMusicBlackMatrixFile.rules)
        val appleNewsBlackMatrixFile = BlackMatrixFile("AppleNews/AppleNews.list")
        rules.addAll(appleNewsBlackMatrixFile.rules)
        val appleProxyBlackMatrixFile = BlackMatrixFile("AppleProxy/AppleProxy.list")
        rules.addAll(appleProxyBlackMatrixFile.rules)
        val appleTVBlackMatrixFile = BlackMatrixFile("AppleTV/AppleTV.list")
        rules.addAll(appleTVBlackMatrixFile.rules)
        val appStoreBlackMatrixFile = BlackMatrixFile("AppStore/AppStore.list")
        rules.addAll(appStoreBlackMatrixFile.rules)
        val siriBlackMatrixFile = BlackMatrixFile("Siri/Siri.list")
        rules.addAll(siriBlackMatrixFile.rules)
        val iCloudBlackMatrixFile = BlackMatrixFile("iCloud/iCloud.list")
        rules.addAll(iCloudBlackMatrixFile.rules)
        val iCloudPrivateRelayBlackMatrixFile = BlackMatrixFile("iCloudPrivateRelay/iCloudPrivateRelay.list")
        rules.addAll(iCloudPrivateRelayBlackMatrixFile.rules)
        val testFlightBlackMatrixFile = BlackMatrixFile("TestFlight/TestFlight.list")
        rules.addAll(testFlightBlackMatrixFile.rules)
        val fitnessPlusBlackMatrixFile = BlackMatrixFile("FitnessPlus/FitnessPlus.list")
        rules.addAll(fitnessPlusBlackMatrixFile.rules)
        val systemOTABlackMatrixFile = BlackMatrixFile("SystemOTA/SystemOTA.list")
        rules.addAll(systemOTABlackMatrixFile.rules)
        val findMyBlackMatrixFile = BlackMatrixFile("FindMy/FindMy.list")
        rules.addAll(findMyBlackMatrixFile.rules)
        val beatsBlackMatrixFile = BlackMatrixFile("Beats/Beats.list")
        rules.addAll(beatsBlackMatrixFile.rules)
    }
}