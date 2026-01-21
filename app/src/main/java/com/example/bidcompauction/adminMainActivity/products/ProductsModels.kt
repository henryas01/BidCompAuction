package com.example.bidcompauction.adminMainActivity.products

data class AdminProduct(
    val id: String,
    val name: String,
    val price: Long,
    val qty: Int,
    val description: String,
    val imageRes: Int
)
