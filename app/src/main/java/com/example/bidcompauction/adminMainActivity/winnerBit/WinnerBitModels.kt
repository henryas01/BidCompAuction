package com.example.bidcompauction.adminMainActivity.winnersBit

data class BidEntryUi(
    val id: String,
    val userId: String,
    val userName: String,
    val productName: String,
    val bidPrice: Long,
    val time: String
)
