package dev.aige.rules.core.entities

sealed class Rule {
    abstract fun format(): String
}