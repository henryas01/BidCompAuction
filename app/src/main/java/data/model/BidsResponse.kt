package com.example.bidcompauction.data.model

import data.model.AdminFlashSaleResponse

data class BidsResponse(
    val id: Int,
    val flashSaleId: Int,
    val userId: Int,
    val basePrice: String,
    val bidPrice: String, // Ubah ke String jika API mengirim string "0"
    val isHighest: Boolean,
    val status: String,
    val createdAt: String,
    val updatedAt: String,
    val flashSale: AdminFlashSaleResponse? // Objek nested dari API
)


