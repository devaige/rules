package dev.aige.rules.provider.blackmatrix.entities

data class BlackMatrixPath(val folder: String, val filename: String = folder) {
    val path: String
        get() = "$folder/$filename.list"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as BlackMatrixPath
        return path == other.path
    }

    override fun hashCode(): Int = path.hashCode()
}