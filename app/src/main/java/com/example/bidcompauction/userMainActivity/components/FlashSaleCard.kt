package com.example.bidcompauction.userMainActivity.components

import com.example.bidcompauction.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
// Hapus import FlashSaleUi jika sudah full menggunakan data dari API
import com.example.bidcompauction.userMainActivity.UserMainUtils.formatCountdown
import com.example.bidcompauction.userMainActivity.UserMainUtils.formatRupiah
import com.example.bidcompauction.userMainActivity.UserMainUtils.rememberRemainingTime
import data.model.AdminFlashSaleResponse
import utils.Constants
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

@Composable
fun FlashSaleCard(
    item: AdminFlashSaleResponse,
    // FIX: Samakan tipe data dengan item (AdminFlashSaleResponse)
    onBidClick: (AdminFlashSaleResponse) -> Unit = {}
) {
    val endEpoch = remember(item.endAt) {
        try {
            val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US).apply {
                timeZone = TimeZone.getTimeZone("UTC")
            }
            val cleanDate = item.endAt.replace("Z", "").split(".")[0]
            sdf.parse(cleanDate)?.time ?: 0L
        } catch (e: Exception) {
            0L
        }
    }

    val remainingMs by rememberRemainingTime(endEpoch)
    val ended = remainingMs <= 0L
    val countdown = if (ended) "ENDED" else formatCountdown(remainingMs)
    val imageUrl = if (!item.images.isNullOrEmpty()) {
        Constants.getFullImageUrl(item.images.lastOrNull())
    } else {
        null
    }

    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF161616)),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        modifier = Modifier
            .width(320.dp)
            .height(190.dp)
    ) {
        Row(Modifier.fillMaxSize()) {

            // --- SISI KIRI: IMAGE ---
            Box(
                modifier = Modifier
                    .width(140.dp)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(topStart = 24.dp, bottomStart = 24.dp))
            ) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = item.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize(),
                    error = painterResource(R.drawable.ic_placeholder),
                    placeholder = painterResource(R.drawable.ic_placeholder)
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Black.copy(alpha = 0.6f), Color.Transparent),
                                endY = 150f
                            )
                        )
                )

                Surface(
                    color = if (ended) Color.Gray else Color(0xFFFF3D00),
                    shape = RoundedCornerShape(bottomEnd = 12.dp),
                    modifier = Modifier.align(Alignment.TopStart)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Filled.Bolt, null, tint = Color.White, modifier = Modifier.size(14.dp))
                        Spacer(Modifier.width(4.dp))
                        Text(
                            text = countdown,
                            color = Color.White,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                }
            }

            // --- SISI KANAN: DETAIL ---
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = item.name,
                        color = Color.White,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Text(
                        text = if (ended) "Auction Finished" else "Hot Auction",
                        color = Color.Gray,
                        fontSize = 11.sp
                    )

                    Spacer(Modifier.height(12.dp))

                    Text("Current Bid", color = Color.Gray, fontSize = 10.sp)

                    Text(
                        // FIX: item.price dari API dikonversi ke Long dengan aman
                        text = formatRupiah(item.price.toString().toLongOrNull() ?: 0L),
                        color = Color(0xFF00FF85),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Black
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Stock: ${item.stock}", // FIX: Gunakan .stock sesuai model AdminFlashSaleResponse
                        color = Color.White.copy(alpha = 0.4f),
                        fontSize = 11.sp
                    )

                    Surface(
                        onClick = { if (!ended) onBidClick(item) },
                        enabled = !ended,
                        shape = RoundedCornerShape(12.dp),
                        color = Color.Transparent,
                        modifier = Modifier
                            .height(36.dp)
                            .background(
                                brush = if (!ended) Brush.horizontalGradient(
                                    listOf(Color(0xFFFFD700), Color(0xFFFFA500))
                                ) else Brush.linearGradient(listOf(Color.Gray, Color.DarkGray)),
                                shape = RoundedCornerShape(12.dp)
                            )
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.Gavel, null, modifier = Modifier.size(14.dp), tint = Color.Black)
                            Spacer(Modifier.width(4.dp))
                            Text("BID", fontWeight = FontWeight.Black, fontSize = 11.sp, color = Color.Black)
                        }
                    }
                }
            }
        }
    }
}