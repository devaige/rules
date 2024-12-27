package dev.aige.rules.core.generator.clash

import dev.aige.rules.core.entities.ClashRule
import dev.aige.rules.provider.blackmatrix.entities.BlackMatrixFile
import dev.aige.rules.provider.sstap.entities.SSTapFile
import dev.aige.rules.provider.sstap.translator.SSTapClashRuleTranslator

class EAClashRuleFileGenerator : ClashRuleFileGenerator("EA.list") {
    override suspend fun generate() = write { rules: MutableSet<ClashRule> ->
        // 读取 BlackMatrix 配置文件
        val eaBlackMatrixFile = BlackMatrixFile("EA/EA.list")
        rules.addAll(eaBlackMatrixFile.rules)

        // 读取 SSTap 配置文件
        val apexSSTapFIle = SSTapFile("Apex.rules")
        apexSSTapFIle.rules.map { rule ->
            rules.add(SSTapClashRuleTranslator(apexSSTapFIle.conf).translate(rule))
        }
        val apexUUSSTapFIle = SSTapFile("Apex-uu.rules")
        apexUUSSTapFIle.rules.map { rule ->
            rules.add(SSTapClashRuleTranslator(apexSSTapFIle.conf).translate(rule))
        }
    }
}