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

enum class BidStatus {
    LEADING,    // Penawaran tertinggi saat ini
    OUTBID,     // Sudah disalip orang lain
    WINNING,    // Lelang berakhir & terpilih oleh admin
    LOST        // Lelang berakhir & tidak terpilih
}

data class MyBidUi(
    val id: String,
    val productName: String,
    val myLastBid: Long,
    val currentHighestBid: Long,
    val status: BidStatus,
    val imageUrl: Int
)

data class BidState(
    val product: FlashSaleUi,
    val currentBid: Long,
    val myBid: Long = currentBid + 50_000
)

enum class MainTab { Home, MyBids, Checkout, Invoice }
