package dev.aige.rules.provider.acl4ssr.entities

data class ACL4SSRRule(
    val type: Type,
    val condition: String,
) {
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
