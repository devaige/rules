package dev.aige.rules.core.translator

import dev.aige.rules.core.entities.Rule

interface RuleTranslator<Input, Output : Rule> {
    suspend fun translate(input: Input): Output
}