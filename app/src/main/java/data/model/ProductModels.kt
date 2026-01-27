package data.model

import android.net.Uri

data class AdminProductResponse(
    val id: Int,
    val name: String,
    val price: Any, // API mengembalikan string di GET tapi number di POST, Any lebih aman
    val images: List<String>,
    val stock: Int,
    val desc: String
)

data class AdminProductRequest(
    val name: String,
    val price: String,
    val stock: String,
    val desc: String,
    val imageUri: Uri? = null
)

data class DeleteResponse(
    val raw: List<Any>,
    val affected: Int
)