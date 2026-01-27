package com.example.bidcompauction.adminMainActivity
//enum class AdminTab { PRODUCTS, FLASH, BIDS, INVOICES }

enum class AdminTab {
    PRODUCTS, FLASHSALE, BIDS, INVOICES
}

object AdminRoute {
    const val PRODUCTS = "admin_products"
    const val FLASHSALE = "admin_flashsale"
    const val BIDS = "admin_BIDS"
    const val INVOICES = "admin_invoices"
}
