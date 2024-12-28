package dev.aige.rules.provider.acl4ssr

import java.io.File

object ACL4SSR {
    private const val ROOT: String = "ACL4SSR"
    val rules: File
        get() = File(ROOT, "Clash/Ruleset")
}