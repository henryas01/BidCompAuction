package com.example.bidcompauction.adminMainActivity
//enum class AdminTab { PRODUCTS, FLASH, WINNERS, INVOICES }

enum class AdminTab {
    PRODUCTS, FLASHSALE, WINNERS, INVOICES
}

object AdminRoute {
    const val PRODUCTS = "admin_products"
    const val FLASHSALE = "admin_flashsale"
    const val WINNERS = "admin_winners"
    const val INVOICES = "admin_invoices"
}
