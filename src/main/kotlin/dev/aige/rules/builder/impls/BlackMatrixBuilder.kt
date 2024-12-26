package dev.aige.rules.builder.impls

import dev.aige.rules.Rule
import dev.aige.rules.Shortcut
import dev.aige.rules.builder.AbstractRuleBuilder
import dev.aige.rules.builder.RuleBuilder

class BlackMatrixBuilder(path: String) : AbstractRuleBuilder(path), RuleBuilder {
    init {
        register(Shortcut(Rule.Name.OPENAI, "$PATH/OpenAI/OpenAI.list"))
    }

    private companion object {
        private const val DIRECTORY: String = "blackmatrix"
        private const val PATH: String = "/rule/Clash"
    }

    override suspend fun build(): List<Rule> = execute(DIRECTORY)
    override fun getRecordByLine(line: String): Rule.Record {
        val elements: List<String> = line.split(",")
        return Rule.Record(Rule.Record.Type.from(elements[0]), elements[1])
    }
}