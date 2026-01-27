package data.model

import android.net.Uri

/**
 * Model data untuk menampung input dari AdminAddFlashSaleDialog
 * sebelum dikonversi menjadi Multipart Request di ViewModel.
 */
data class AdminFlashSaleRequest(
    val name: String,
    val price: Long,
    val stock: Int,
    val desc: String,
    val startAt: String, // Format ISO8601: "2026-01-26T10:00:00Z"
    val endAt: String,   // Format ISO8601: "2026-01-27T10:00:00Z"
    val imageUri: Uri?   // URI gambar dari Gallery picker
)