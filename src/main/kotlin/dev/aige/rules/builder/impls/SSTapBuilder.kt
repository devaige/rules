package dev.aige.rules.builder.impls

import dev.aige.rules.Rule
import dev.aige.rules.builder.AbstractRuleBuilder
import dev.aige.rules.builder.RuleBuilder

class SSTapBuilder(path: String) : AbstractRuleBuilder(path), RuleBuilder {
    private companion object {
        private const val DIRECTORY: String = "sstap"
    }

    override suspend fun build(): List<Rule> = execute(DIRECTORY)
    override fun getRecordByLine(line: String): Rule.Record {
        TODO("Not yet implemented")
    }
}