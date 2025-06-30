package dev.aige.rules

import dev.aige.rules.core.generator.RuleFileGenerator
import dev.aige.rules.core.generator.clash.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit
import java.io.File
import java.nio.file.Paths

const val OUTPUT: String = "mine"

private val generators: List<RuleFileGenerator<*>> = listOf(
    DirectClashRuleFileGenerator(),
    OpenAIClashRuleFileGenerator(),
    ClaudeClashRuleFileGenerator(),
    PerplexityClashRuleFileGenerator(),
    GoogleClashRuleFileGenerator(),
    TelegramClashRuleFileGenerator(),
    TwitterClashRuleFileGenerator(),
    BinanceClashRuleFileGenerator(),
    GitHubClashRuleFileGenerator(),
    LineClashRuleFileGenerator(),
    NetflixClashRuleFileGenerator(),
    TikTokClashRuleFileGenerator(),
    RedditClashRuleFileGenerator(),
    AppleClashRuleFileGenerator(),
    MetaClashRuleFileGenerator(),
    MicrosoftClashRuleFileGenerator(),
    StripeClashRuleFileGenerator(),
    PCClashRuleFileGenerator(),
    EAClashRuleFileGenerator(),
    ProxyClashRuleFileGenerator(),
    RejectClashRuleFileGenerator(),
    YoutubeClashRuleFileGenerator(),
    GrokClashRuleFileGenerator(),
    GeminiClashRuleFileGenerator(),
    MiningClashRuleFileGenerator(),
)

fun main() = runBlocking {
    val output: File = Paths.get(OUTPUT).toAbsolutePath().toFile()
    if (output.exists() && output.isDirectory) output.deleteRecursively()
    output.mkdirs()
    val semaphore = Semaphore(8)
    generators.forEach {
        launch {
            semaphore.withPermit { it.generate() }
        }
    }
}