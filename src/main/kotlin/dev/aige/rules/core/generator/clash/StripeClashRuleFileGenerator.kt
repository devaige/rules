package dev.aige.rules.core.generator.clash

import dev.aige.rules.core.entities.ClashRule
import dev.aige.rules.provider.blackmatrix.entities.BlackMatrixFile

class StripeClashRuleFileGenerator : ClashRuleFileGenerator("Stripe.list") {
    override suspend fun generate() = write { rules: MutableSet<ClashRule> ->
        // 读取 BlackMatrix 配置文件
        val stripeBlackMatrixFile = BlackMatrixFile("Stripe/Stripe.list")
        rules.addAll(stripeBlackMatrixFile.rules)
    }
}