package com.example.bidcompauction.userMainActivity.myBids.components

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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
    bidResponse: BidsResponse, // UBAH: Gunakan BidsResponse dari API
    onBidAgain: () -> Unit,
    onPayNow: () -> Unit
) {
    // Mapping Status dari String API ke Enum Lokal
    val currentStatus = when (bidResponse.status.uppercase()) {
        "WINNING" -> BidStatus.WINNING
        "OUTBID" -> BidStatus.OUTBID
        else -> BidStatus.LEADING // Default untuk PENDING/LEADING
    }

    val statusColor = when (currentStatus) {
        BidStatus.LEADING -> Color(0xFF00FF85)
        BidStatus.OUTBID -> Color(0xFFFF4545)
        BidStatus.WINNING -> Color(0xFFFFD700)
        else -> Color.Gray
    }

    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1A1A)),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(70.dp),
                color = Color(0xFF252525),
                shape = RoundedCornerShape(12.dp)
            ) {
                AsyncImage(
                    model = bidResponse.flashSale?.images?.firstOrNull()?.let { Constants.getFullImageUrl(it) },
                    contentDescription = bidResponse.flashSale?.name,
                    modifier = Modifier.size(70.dp).clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop,
                    error = painterResource(R.drawable.ic_placeholder)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                // Nama Produk (Gunakan FlashSaleId jika nama produk tidak ada di response)
                Text(
                    text = bidResponse.flashSale?.name ?: "Unknown Product",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    maxLines = 1
                )

                Text(
                    text = "Your Bid: ${formatRupiah(bidResponse.bidPrice.toLongOrNull() ?: 0L)}",
                    color = Color.Gray,
                    fontSize = 13.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Status Badge
                Surface(
                    color = statusColor.copy(alpha = 0.15f),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        text = bidResponse.status, // Menampilkan status asli dari API
                        color = statusColor,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Black
                    )
                }
            }

            // AKSI BERDASARKAN STATUS
            when (currentStatus) {
                BidStatus.OUTBID -> {
                    IconButton(onClick = onBidAgain) {
                        Icon(Icons.Default.Refresh, contentDescription = null, tint = Color.White)
                    }
                }
                BidStatus.WINNING -> {
                    Button(
                        onClick = onPayNow,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00FF85)),
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(horizontal = 12.dp)
                    ) {
                        Icon(Icons.Default.Payment, null, modifier = Modifier.size(14.dp), tint = Color.Black)
                        Spacer(Modifier.width(4.dp))
                        Text("PAY", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 10.sp)
                    }
                }
                else -> { /* LEADING: Menunggu update */ }
            }
        }
    }
}