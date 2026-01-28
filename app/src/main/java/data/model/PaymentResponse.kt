package data.model

data class PaymentResponse(
    val id: Int,
    val userId: Int,
    val sourceType: String,
    val sourceId: Int,
    val amount: String,
    val status: String,
    val proofImages: List<String>,
    val invoice: InvoiceDetailResponse? // Tambahkan ini agar tidak error
)

data class InvoiceDetailResponse(
    val id: Int,
    val paymentId: Int,
    val invoiceNumber: String,
    val amount: String,
    val createdAt: String
)