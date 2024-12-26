package dev.aige.rules.exporter.impls

import dev.aige.rules.exporter.AbstractRuleExporter
import dev.aige.rules.exporter.Exporter
import dev.aige.rules.provider.blackmatrix.file.BlackMatrixClashRuleFile
import java.io.File

class OpenAIRuleExporter : AbstractRuleExporter("OpenAI.list"), Exporter {
    init {
        register(BlackMatrixClashRuleFile(File("blackmatrix/rule/Clash/OpenAI/OpenAI.list")))
    }
}