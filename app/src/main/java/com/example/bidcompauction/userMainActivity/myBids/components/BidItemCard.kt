package com.example.bidcompauction.userMainActivity.myBids.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.bidcompauction.R
import com.example.bidcompauction.data.model.BidsResponse
import com.example.bidcompauction.userMainActivity.BidStatus
import com.example.bidcompauction.userMainActivity.UserMainUtils.formatRupiah
import data.model.AdminFlashSaleResponse
import utils.Constants


@Composable
fun BidItemCard(
    bidResponse: BidsResponse,
    onBidAgain: () -> Unit,
    onPayNow: () -> Unit
) {
    val status = bidResponse.status.uppercase()

    // Warna berdasarkan status API
    val statusColor = when (status) {
        "WINNER" -> Color(0xFFFFD700) // Gold
        "OUTBID" -> Color(0xFFFF4545) // Merah
        "PAID" -> Color(0xFF00FF85)   // Hijau
        else -> Color(0xFF00FF85)     // Default Leading (Hijau)
    }

    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1A1A)),
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            // ... (Bagian Image dan Text Nama Produk tetap sama) ...

            Column(modifier = Modifier.weight(1f)) {
                Text(bidResponse.flashSale?.name ?: "Item", color = Color.White, fontWeight = FontWeight.Bold)
                Text("Bid: ${formatRupiah(bidResponse.bidPrice.toLongOrNull() ?: 0L)}", color = Color.Gray)

                // Status Badge
                Surface(
                    color = statusColor.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        text = status,
                        color = statusColor,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // AKSI
            when (status) {
                "WINNER" -> {
                    Button(
                        onClick = onPayNow,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00FF85)),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("PAY NOW", color = Color.Black, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
                "OUTBID" -> {
                    IconButton(onClick = onBidAgain) {
                        Icon(Icons.Default.Refresh, contentDescription = null, tint = Color.White)
                    }
                }
                "PAID" -> {
                    Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color(0xFF00FF85))
                }
            }
        }
    }
}