package dev.aige.rules.core.entities.standard

data class BaseClashRule(
    val type: Type,
    val condition: String,
    val strategy: Strategy,
    val options: Map<String, String>? = null
) : BaseRule() {
    override fun format(): String {
        val sb = StringBuilder()
        sb.append(type.value).append(",").append(condition).append(",").append(strategy)
        if (null != options) sb.append(",").append(options.entries.joinToString(",") { "${it.key}=${it.value}" })
        return sb.toString()
    }

    enum class Type(val value: String) {
        DOMAIN("DOMAIN"),
        DOMAIN_SUFFIX("DOMAIN-SUFFIX"),
        DOMAIN_KEYWORD("DOMAIN-KEYWORD"),
        GEO_IP("GEOIP"),
        IP_CIDR("IP-CIDR"),
        IP_CIDR6("IP-CIDR6"),
        SRC_IP_CIDR("SRC-IP-CIDR"),
        SRC_PORT("SRC-PORT"),
        DST_PORT("DST-PORT"),
        PROCESS_NAME("PROCESS-NAME"),
        PROCESS_PATH("PROCESS-PATH"),
        IP_SET("IPSET"),
        RULE_SET("RULE-SET"),
        SCRIPT("SCRIPT"),
        IP_ASN("IP-ASN"),
        MATCH("MATCH");

        companion object {
            private val CACHE = Type.entries.associateBy(Type::value)
            fun from(value: String): Type = CACHE[value]!!
        }
    }

    sealed class Strategy {
        override fun toString(): String = this::class.simpleName!!

        data object Direct : Strategy()
        data object Reject : Strategy()
        data object Proxy : Strategy()
        data class CustomStrategyGroup(val name: String) : Strategy() {
            override fun toString(): String = name
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as BaseClashRule
        if (type != other.type) return false
        if (condition != other.condition) return false
        if (strategy != other.strategy) return false
        if (options != other.options) return false
        return true
    }

    override fun hashCode(): Int {
        var result = type.hashCode()
        result = 31 * result + condition.hashCode()
        result = 31 * result + strategy.hashCode()
        result = 31 * result + (options?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String =
        "StandardClashRule(type=$type, condition='$condition', strategy=$strategy, options=$options)"
}
