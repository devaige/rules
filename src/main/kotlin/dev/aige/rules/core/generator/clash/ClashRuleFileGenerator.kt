package dev.aige.rules.core.generator.clash

import dev.aige.rules.OUTPUT
import dev.aige.rules.core.entities.ClashRule
import dev.aige.rules.core.generator.RuleFileGenerator
import dev.aige.rules.core.optimizer.ClashRuleOptimizer
import java.io.File
import java.nio.file.Paths

abstract class ClashRuleFileGenerator(filename: String) : RuleFileGenerator<ClashRule>(filename) {
    private val folder: File
        get() {
            val folder: File = Paths.get("$OUTPUT/clash").toAbsolutePath().toFile()
            if (!folder.exists() || !folder.isDirectory) folder.mkdirs()
            return folder
        }
    override val output: File
        get() = File(folder, filename)

    /**
     * 对所有 Clash 规则文件自动执行优化（域名去冗 + IP-CIDR 聚合）。
     */
    override fun postProcess(rules: MutableSet<ClashRule>): Set<ClashRule> =
        ClashRuleOptimizer.optimize(rules)
}