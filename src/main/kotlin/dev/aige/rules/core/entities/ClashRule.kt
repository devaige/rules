package dev.aige.rules.core.entities

/**
 * Clash 规则对象
 * 这里忽略了规则策略，因为策略由具体不同的代理软件进行定义，本脚本只针对规则进行整理
 *
 * @param type 规则类型
 * @param argument 规则参数
 * @param isDNSResolve 是否进行 DNS 解析
 */
data class ClashRule(
    val type: Type,
    val argument: String,
    val isDNSResolve: Boolean
) : Rule() {
    companion object {
        const val DNS_RESOLVE: String = "no-resolve"
    }

    override fun format(): String = toString()
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as ClashRule
        if (type != other.type) return false
        if (argument != other.argument) return false
        if (isDNSResolve != other.isDNSResolve) return false
        return true
    }

    override fun hashCode(): Int {
        var result = type.hashCode()
        result = 31 * result + argument.hashCode()
        result = 31 * result + isDNSResolve.hashCode()
        return result
    }

    override fun toString(): String {
        val sb = StringBuilder()
        sb.append(type.value).append(",").append(argument)
        if (isDNSResolve) sb.append(",").append(DNS_RESOLVE)
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
}