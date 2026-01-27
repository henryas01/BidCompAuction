package data.model

data class AdminFlashSaleResponse(
    val id: Int,
    val name: String,
    val price: Any, // Bisa String atau Int dari API
    val stock: Int,
    val desc: String,
    val images: List<String>,
    val startAt: String,
    val endAt: String,
    val isActive: Boolean,
    val isExpired: Boolean,
    val countdown: CountdownInfo? = null
)

data class CountdownInfo(
    val days: Int,
    val hours: Int,
    val minutes: Int,
    val expired: Boolean
)