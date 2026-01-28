package data.model

data class InvoiceMeResponse(
    val id: Int,
    val paymentId: Int,
    val invoiceNumber: String,
    val amount: String,
    val createdAt: String
)

// Karena endpoint invoice/me mengembalikan list data Payment yang berisi Invoice
data class UserPaymentInvoiceResponse(
    val id: Int,
    val userId: Int,
    val sourceType: String, // "BID" or "PRODUCT"
    val sourceId: Int,
    val amount: String,
    val status: String,
    val createdAt: String,
    val invoice: InvoiceMeResponse? // Nested object dari API
)