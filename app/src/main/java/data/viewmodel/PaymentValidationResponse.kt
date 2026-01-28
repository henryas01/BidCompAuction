package data.viewmodel


data class PaymentValidationResponse(
    val id: Int,
    val paymentId: Int,
    val invoiceNumber: String,
    val amount: String,
    val createdAt: String
)