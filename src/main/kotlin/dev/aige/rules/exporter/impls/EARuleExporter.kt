package dev.aige.rules.exporter.impls

import dev.aige.rules.exporter.AbstractRuleExporter
import dev.aige.rules.exporter.Exporter
import dev.aige.rules.provider.blackmatrix.file.BlackMatrixClashRuleFile
import dev.aige.rules.provider.sstap.SSTapRuleFile
import java.io.File

class EARuleExporter : AbstractRuleExporter("EA.list"), Exporter {
    init {
        register(BlackMatrixClashRuleFile(File("blackmatrix/rule/Clash/EA/EA.list")))
        register(SSTapRuleFile(File("sstap/rules/Apex.rules")))
        register(SSTapRuleFile(File("sstap/rules/Apex-uu.rules")))
    }
}