package dev.aige.rules.core.generator.clash

import dev.aige.rules.core.entities.ClashRule
import dev.aige.rules.provider.acl4ssr.entities.ACL4SSRFile
import dev.aige.rules.provider.blackmatrix.entities.BlackMatrixFile
import dev.aige.rules.provider.blackmatrix.entities.BlackMatrixPath

class GoogleClashRuleFileGenerator : ClashRuleFileGenerator("Google.list") {
    private val blackMatrixFilePaths: Set<BlackMatrixPath> = setOf(
        BlackMatrixPath("Gemini"),
        BlackMatrixPath("BardAI"),
        BlackMatrixPath("Google"),
        BlackMatrixPath("GoogleDrive"),
        BlackMatrixPath("GoogleEarth"),
        BlackMatrixPath("GoogleFCM"),
        BlackMatrixPath("GoogleSearch"),
        BlackMatrixPath("GoogleVoice"),
        BlackMatrixPath("Chromecast"),
    )
    private val acL4SSRFiles: Set<ACL4SSRFile> = setOf(
        ACL4SSRFile("Gemini"),
        ACL4SSRFile("Google"),
        ACL4SSRFile("GoogleCN"),
        ACL4SSRFile("GoogleCNProxyIP"),
        ACL4SSRFile("GoogleEarth"),
        ACL4SSRFile("GoogleFCM"),
        ACL4SSRFile("Scholar"),
    )

    override suspend fun generate() = write { rules: MutableSet<ClashRule> ->
        // 读取 BlackMatrix 配置文件
        rules.addAll(blackMatrixFilePaths.flatMap { BlackMatrixFile(it.path).rules })
        // 读取 ACL4SSR 配置文件
        rules.addAll(acL4SSRFiles.flatMap { it.rules })
    }
}