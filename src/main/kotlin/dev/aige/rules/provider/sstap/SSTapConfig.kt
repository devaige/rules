package dev.aige.rules.provider.sstap

// SSTap-Rule 规则文件的配置信息
// 参考：https://github.com/devaige/SSTap-Rule/blob/master/doc/home.md
data class SSTapConfig(
    val nameInEN: String,// 规则英文名
    val nameInCN: String,// 规则中文名
    val aProxyType: ProxyType,// 代理类型；0：代理、1：直连
    val bProxyType: ProxyType,// 代理类型；0：代理、1：直连
    val placeholderValue1: Int,// 未知
    val placeholderValue2: Int,// 未知
    val canReadAndWrite: Boolean,// 是否可读写；0：可读写、1：只可读
    val dnsProxyType: DNSProxyType,// DNS代理类型；0：自动、1：直连、2：代理
    val note: String,// 备注
) {
    val isDNSResolve: Boolean
        get() = when (dnsProxyType) {
            DNSProxyType.PROXY -> true
            else -> false
        }

    enum class ProxyType(val type: Int) {
        PROXY(0), DIRECT(1);

        companion object {
            private val CACHE = ProxyType.entries.associateBy(ProxyType::type)
            fun from(type: Int): ProxyType = CACHE[type]!!
        }
    }


    enum class DNSProxyType(val type: Int) {
        AUTO(0), DIRECT(1), PROXY(2);

        companion object {
            private val CACHE = DNSProxyType.entries.associateBy(DNSProxyType::type)
            fun from(type: Int): DNSProxyType = CACHE[type]!!
        }
    }
}