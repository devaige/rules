package dev.aige.rules.core.generator.clash

import dev.aige.rules.core.entities.ClashRule
import dev.aige.rules.provider.blackmatrix.entities.BlackMatrixFile

class MetaClashRuleFileGenerator : ClashRuleFileGenerator("Meta.list") {
    override suspend fun generate() = write { rules: MutableSet<ClashRule> ->
        // 读取 BlackMatrix 配置文件
        val facebookBlackMatrixFile = BlackMatrixFile("Facebook/Facebook.list")
        rules.addAll(facebookBlackMatrixFile.rules)
        val instagramBlackMatrixFile = BlackMatrixFile("Instagram/Instagram.list")
        rules.addAll(instagramBlackMatrixFile.rules)
        val whatsappBlackMatrixFile = BlackMatrixFile("Whatsapp/Whatsapp.list")
        rules.addAll(whatsappBlackMatrixFile.rules)
        val threadsBlackMatrixFile = BlackMatrixFile("Threads/Threads.list")
        rules.addAll(threadsBlackMatrixFile.rules)
    }
}