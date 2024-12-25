package dev.aige.rules

import java.nio.file.Paths

fun main() {
    println("当前目录：${Paths.get("").toAbsolutePath()}")
}