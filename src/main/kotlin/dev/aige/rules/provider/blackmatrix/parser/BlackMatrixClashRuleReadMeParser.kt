package dev.aige.rules.provider.blackmatrix.parser

import com.vladsch.flexmark.ast.Link
import com.vladsch.flexmark.ast.Text
import com.vladsch.flexmark.ext.tables.*
import com.vladsch.flexmark.parser.Parser
import com.vladsch.flexmark.util.ast.Node
import java.io.File
import java.net.URI
import java.nio.file.Paths

fun main() {
    val file: File = Paths.get("blackmatrix/rule/Clash/README.md").toFile()
    val content: String = file.readText()
    val result = parseMarkdownTablesByFirstColumn(content)
    result.forEach { (title, links) ->
//        if (title == "\uD83D\uDCF5Advertising") {// 📵Advertising
//        if (title == "\uD83C\uDF0FGlobal") {// 🌏Global
//        if (title == "\uD83C\uDF0FGlobalMedia") {// 🌏GlobalMedia
//        if (title == "\uD83C\uDDE8\uD83C\uDDF3Mainland") {// 🇨🇳Mainland
//        if (title == "\uD83C\uDDE8\uD83C\uDDF3MainlandMedia") {// 🇨🇳MainlandMedia
//        if (title == "\uD83D\uDCFAMedia") {// 📺Media
//        if (title == "\uD83C\uDFAEGame") {// 🎮Game
//        if (title == "\uD83D\uDEABReject") {// 🚫Reject
        if (title == "\uD83D\uDDA5\uFE0FOther") {// 🖥️Other
            println("Table Title: $title")
            links.forEach { link ->
                // 将链接转换为代码：
                println("\"${URI.create(link).path.split("/").last()}\",")
            }
        }
    }
}

fun parseMarkdownTablesByFirstColumn(markdownText: String): Map<String, List<String>> {
    // 配置支持表格解析的 Flexmark
    val options = listOf(TablesExtension.create())
    val parser = Parser.builder().extensions(options).build()
    val document: Node = parser.parse(markdownText)

    // 存储表格标题和对应的超链接内容
    val tableLinksByTitle = mutableMapOf<String, MutableList<String>>()

    // 遍历文档的 AST
    var currentNode = document.firstChild
    while (currentNode != null) {
        if (currentNode is TableBlock) {
            // 提取表格标题（TableHead 的第一行第一列内容）
            val tableTitle = extractTitleFromTableHead(currentNode)?.trim() ?: "Untitled"

            // 提取表格中的超链接
            val links = mutableListOf<String>()
            extractLinksFromTable(currentNode, links)

            // 分类存储
            tableLinksByTitle[tableTitle] = links
        }
        currentNode = currentNode.next
    }

    return tableLinksByTitle
}

fun extractTitleFromTableHead(tableBlock: TableBlock): String? {
    val tableHead = tableBlock.firstChild
    if (tableHead is TableHead) {
        val firstRow = tableHead.firstChild
        if (firstRow is TableRow) {
            val firstCell = firstRow.firstChild
            if (firstCell is TableCell) {
                return extractTextFromNode(firstCell) // 提取第一列内容
            }
        }
    }
    return null
}

fun extractLinksFromTable(tableBlock: TableBlock, links: MutableList<String>) {
    var currentNode = tableBlock.firstChild
    while (currentNode != null) {
        if (currentNode is TableBody || currentNode is TableHead) {
            var rowNode = currentNode.firstChild
            while (rowNode != null) {
                if (rowNode is TableRow) {
                    var cellNode = rowNode.firstChild
                    while (cellNode != null) {
                        if (cellNode is TableCell) {
                            // 在单元格中查找超链接
                            extractLinksFromNode(cellNode, links)
                        }
                        cellNode = cellNode.next
                    }
                }
                rowNode = rowNode.next
            }
        }
        currentNode = currentNode.next
    }
}

fun extractLinksFromNode(node: Node, links: MutableList<String>) {
    var childNode = node.firstChild
    while (childNode != null) {
        if (childNode is Link) {
            links.add(childNode.url.toString()) // 提取超链接 URL
        }
        // 递归检查子节点
        extractLinksFromNode(childNode, links)
        childNode = childNode.next
    }
}

fun extractTextFromNode(node: Node): String {
    val textBuilder = StringBuilder()
    var currentNode = node.firstChild
    while (currentNode != null) {
        if (currentNode is Text) {
            textBuilder.append(currentNode.chars)
        }
        // 递归获取子节点的文本内容
        textBuilder.append(extractTextFromNode(currentNode))
        currentNode = currentNode.next
    }
    return textBuilder.toString()
}