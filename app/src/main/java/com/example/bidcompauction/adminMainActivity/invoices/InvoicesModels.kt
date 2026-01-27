package com.example.bidcompauction.adminMainActivity.invoices

data class InvoiceAdminUi(
    val id: String,
    val userName: String,
    val total: Long,
    val status: String,
    val date: String
)


data class AdminInvoice(
    val id: String,
    val customerName: String,
    val productName: String,
    val amount: Long,
    val date: String
)