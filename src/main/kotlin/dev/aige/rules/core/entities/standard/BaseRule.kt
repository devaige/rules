package dev.aige.rules.core.entities.standard

sealed class BaseRule {
    abstract fun format(): String
}