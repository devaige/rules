package dev.aige.rules

import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader

class SSTapCon(private val file: File) {
    private val rules: MutableList<Rule> = mutableListOf()

    fun build() {
        BufferedReader(InputStreamReader(FileInputStream(file), Charsets.UTF_8)).useLines { lines ->
            lines.forEachIndexed { index: Int, line: String ->
                // 校验规则文件的第一行是否以 # 开头
                var l: String = line.trim()
                if (index == 0) {
                    if (!l.startsWith("#"))
                        throw IllegalArgumentException("SSTap rule file $file")
                    // #300hero-cn,300英雄,0,0,1,0,1,0,By-FQrabbit
                    l = l.removePrefix("#")// 去掉第一行开头的 #
                    val parts: List<String> = l.split(",")

                }
                if (index == 0 && !l.startsWith("#")) {
                }
                throw IllegalArgumentException("SSTap rule file $file")

            }
        }
    }

    fun getRules(): List<Rule> = rules
}