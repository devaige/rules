package dev.aige.rules.exporter

import java.io.File

interface Exporter {
    suspend fun export(folder: File)
}