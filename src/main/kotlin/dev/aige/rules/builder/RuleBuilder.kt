package dev.aige.rules.builder

import dev.aige.rules.Rule

interface RuleBuilder {
    suspend fun build(): List<Rule>
}