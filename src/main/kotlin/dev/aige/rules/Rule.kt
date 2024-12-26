package dev.aige.rules

data class Rule(val name: Name, val rules: List<Record>) {
    enum class Name(val filename: String) {
        OPENAI("OpenAI.list")
    }

    data class Record(val type: Type, val value: String) {
        enum class Type(val str: String) {
            IP_ASN("IP-ASN"),
            IP_CIDR("IP-CIDR"),
            DOMAIN("DOMAIN"),
            KEYWORD("DOMAIN-KEYWORD"),
            SUFFIX("DOMAIN-SUFFIX");

            companion object {
                private val CACHE = entries.associateBy(Type::str)
                fun from(str: String): Type = CACHE[str]!!
            }
        }
    }
}