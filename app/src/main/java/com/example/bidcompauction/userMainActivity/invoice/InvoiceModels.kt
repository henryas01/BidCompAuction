package com.example.bidcompauction.userMainActivity.invoice


enum class InvoiceStatus {
    PAID,
}

//data class InvoiceUi(
//    val id: String,
//    val date: String,
//    val total: Long,
//    val status: InvoiceStatus
//)


enum class TransactionType {
    BID,     // Hasil Menang Lelang
    DIRECT   // Hasil Checkout Biasa
}

data class InvoiceUi(
    val id: String,
    val date: String,
    val productName: String, // Tambahkan ini
    val total: Long,
    val status: InvoiceStatus,
    val type: TransactionType // Tambahkan ini
)