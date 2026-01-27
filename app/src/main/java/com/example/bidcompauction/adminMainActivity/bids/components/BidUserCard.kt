package com.example.bidcompauction.adminMainActivity.bids.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bidcompauction.adminMainActivity.bids.AdminBidEntry
import com.example.bidcompauction.adminMainActivity.formatRupiah
import com.example.bidcompauction.data.model.BidsResponse

@Composable
fun BidUserCard(
    bid: BidsResponse,
    isTopBid: Boolean,
    onPickWinner: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1A1A)),
        border = BorderStroke(
            width = 1.dp,
            color = if (bid.status == "WINNER") Color(0xFF00FF85) else if (isTopBid) Color(0xFFFFD700).copy(0.5f) else Color.White.copy(0.1f)
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "User ID: ${bid.userId}", // Atau bid.user.name jika ada di API
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Text(bid.flashSale?.name ?: "Unknown Product", color = Color.Gray, fontSize = 12.sp)
                Text(
                    text = formatRupiah(bid.bidPrice.toLongOrNull() ?: 0L),
                    color = Color(0xFF00FF85),
                    fontWeight = FontWeight.Black,
                    fontSize = 18.sp
                )
            }

            if (bid.status == "WINNER") {
                Icon(Icons.Default.CheckCircle, "Won", tint = Color(0xFF00FF85))
            } else {
                Button(
                    onClick = onPickWinner,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isTopBid) Color(0xFFFFD700) else Color(0xFF252525)
                    )
                ) {
                    Text("Pick Winner", fontSize = 10.sp, color = if (isTopBid) Color.Black else Color.White)
                }
            }
        }
    }
}