package com.example.bidcompauction.userMainActivity

data class ProductUi(
    val id: String,
    val name: String,
    val price: Long,
    val qty: Int,
    val imageRes: Int
)

data class FlashSaleUi(
    val id: String,
    val name: String,
    val originalPrice: Long,
    val salePrice: Long,
    val qty: Int,
    val endsAtEpochMs: Long,
    val imageRes: Int
)

enum class MainTab { Home, Checkout, Invoice }
