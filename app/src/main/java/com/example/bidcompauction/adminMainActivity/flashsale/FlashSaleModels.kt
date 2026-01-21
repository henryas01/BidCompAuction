package com.example.bidcompauction.adminMainActivity.flashsale

data class AdminFlashSale(
    val id: String,
    val name: String,
    val price: Long,
    val qty: Int,
    val description: String,
    val imageRes: Int,
    val endsAtEpochMs: Long
)