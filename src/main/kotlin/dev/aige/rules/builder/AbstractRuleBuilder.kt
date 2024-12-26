package dev.aige.rules.builder

import dev.aige.rules.Rule
import dev.aige.rules.Shortcut
import java.io.File

abstract class AbstractRuleBuilder(private val path: String) {
    private val shortcuts: MutableSet<Shortcut> = mutableSetOf()
    protected fun register(shortcut: Shortcut) = shortcuts.add(shortcut)
    protected fun execute(folder: String): List<Rule> {
        val rules: MutableList<Rule> = mutableListOf()
        shortcuts.forEach { shortcut: Shortcut ->
            val name: Rule.Name = shortcut.name// 规则文件名；例如：OpenAI.list
            val path = "$folder${shortcut.path}"// 规则对应的文件路径；例如：/rule/Clash/OpenAI/OpenAI.list
            println("开始读取文件：$path")
            // 读取规则文件中的每一行，将每一行的内容转换为 Rule.Record 对象，然后添加到 rules 中
            var records: MutableList<Rule.Record>? = null
            File(path).forEachLine { line: String ->
                if (filter(line)) return@forEachLine
                println("读取规则：$line")
                if (null == records) records = mutableListOf()
                records!!.add(getRecordByLine(line))
                println("添加规则：$line")
            }
            if (null != records) rules.add(Rule(name, records!!))
        }
        return rules
    }

    protected open fun filter(line: String): Boolean = line.startsWith("#")
    protected abstract fun getRecordByLine(line: String): Rule.Record
}