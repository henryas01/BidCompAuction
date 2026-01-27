package com.example.bidcompauction.adminMainActivity.bids

data class BidEntryUi(
    val id: String,
    val userId: String,
    val userName: String,
    val productName: String,
    val bidPrice: Long,
    val time: String
)


data class AdminBidEntry(
    val id: String,
    val userName: String,
    val bidAmount: Long,
    val time: String,
    val isWinner: Boolean = false
)