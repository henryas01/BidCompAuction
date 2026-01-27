package utils

object Constants {
    const val BASE_URL = "http://192.168.1.10:3000"

    fun getFullImageUrl(imagePath: String?): String? {
        // 1. Jika null atau kosong, return null agar AsyncImage lari ke 'error/placeholder'
        if (imagePath.isNullOrBlank()) return null

        // 2. Cek apakah ini path valid (misal: harus mengandung "/uploads/")
        // Jika isinya cuma "RAM", kondisi ini akan mengembalikan null
        if (!imagePath.contains("/uploads/")) return null

        val cleanBase = BASE_URL.removeSuffix("/")
        val cleanPath = if (imagePath.startsWith("/")) imagePath else "/$imagePath"

        return "$cleanBase$cleanPath"
    }
}