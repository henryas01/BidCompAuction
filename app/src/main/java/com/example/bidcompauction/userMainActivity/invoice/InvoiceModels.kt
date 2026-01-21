package com.example.bidcompauction.userMainActivity.invoice


enum class InvoiceStatus {
    PAID, UNPAID, EXPIRED
}

data class InvoiceUi(
    val id: String,
    val date: String,
    val total: Long,
    val status: InvoiceStatus
)
