package dev.aige.rules.adapter

import dev.aige.rules.core.ClashRule

interface ClashRuleTranslator {
    suspend fun translate(): List<ClashRule>
}