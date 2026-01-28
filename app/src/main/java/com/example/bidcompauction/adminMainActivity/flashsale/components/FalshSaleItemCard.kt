package com.example.bidcompauction.adminMainActivity.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
import com.example.bidcompauction.R
import com.example.bidcompauction.adminMainActivity.formatRupiah
import data.model.AdminFlashSaleResponse
import utils.Constants
import kotlin.math.max

@Composable
fun FlashSaleItemCard(
    item: AdminFlashSaleResponse, // FIX: Menggunakan model API Response
    remainingMs: Long,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    // Logika Timer
    val seconds = max(0, (remainingMs / 1000) % 60)
    val minutes = max(0, (remainingMs / (1000 * 60)) % 60)
    val hours = max(0, (remainingMs / (1000 * 60 * 60)))
    val timerText = String.format("%02d:%02d:%02d", hours, minutes, seconds)

    // Ambil URL Gambar (Asumsi images adalah List<String>)
    val imageUrl = if (!item.images.isNullOrEmpty()) {
        Constants.getFullImageUrl(item.images.lastOrNull())
    } else {
        null
    }


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(135.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF141414)),
        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.08f))
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            // --- SISI KIRI: ASYNC IMAGE + TIMER BADGE ---
            Box(modifier = Modifier.width(115.dp).fillMaxHeight()) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = item.name,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                    error = painterResource(R.drawable.ic_placeholder),
                    placeholder = painterResource(R.drawable.ic_placeholder)
                )

                // Gradient Overlay (Lebih halus ke arah info)
                Box(modifier = Modifier.fillMaxSize().background(
                    Brush.horizontalGradient(
                        colors = listOf(Color.Transparent, Color(0xFF141414)),
                        startX = 0f,
                        endX = 300f
                    )
                ))

                // Floating Timer Badge
                Surface(
                    color = if (remainingMs > 0) Color(0xFFFFD700) else Color.Gray,
                    shape = RoundedCornerShape(bottomEnd = 12.dp)
                ) {
                    Text(
                        text = if (remainingMs > 0) timerText else "ENDED",
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        style = androidx.compose.ui.text.TextStyle(
                            color = Color.Black,
                            fontWeight = FontWeight.Black,
                            fontSize = 10.sp
                        )
                    )
                }
            }

            // --- TENGAH: INFO KONTEN ---
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(vertical = 12.dp, horizontal = 8.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = item.name,
                    color = Color.White,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 16.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = item.descriptions ?: "", // Menggunakan .desc dari API
                    color = Color.White.copy(alpha = 0.5f),
                    fontSize = 10.sp,
                    maxLines = 2,
                    lineHeight = 13.sp,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(vertical = 4.dp)
                )

                // Konversi harga ke Long/Double sebelum formatRupiah
                val priceValue = item.price.toString().toDoubleOrNull()?.toLong() ?: 0L
                Text(
                    text = formatRupiah(priceValue),
                    color = Color(0xFF00FF85),
                    fontWeight = FontWeight.Black,
                    fontSize = 15.sp
                )

                Spacer(Modifier.height(8.dp))

                // Stock Badge
                Surface(
                    color = Color(0xFF00FF85).copy(alpha = 0.12f),
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(0.5.dp, Color(0xFF00FF85).copy(alpha = 0.3f))
                ) {
                    Text(
                        text = "STOCK: ${item.stock}", // Menggunakan .stock dari API
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                        color = Color(0xFF00FF85),
                        fontSize = 9.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            }

            // --- SISI KANAN: ACTIONS ---
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(50.dp)
                    .padding(vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Surface(
                    onClick = onEdit,
                    shape = androidx.compose.foundation.shape.CircleShape,
                    color = Color(0xFF222222),
                    modifier = Modifier.size(36.dp),
                    border = BorderStroke(1.dp, Color.White.copy(alpha = 0.1f))
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(Icons.Default.Edit, null, tint = Color(0xFFFFD700), modifier = Modifier.size(16.dp))
                    }
                }
                Surface(
                    onClick = onDelete,
                    shape = androidx.compose.foundation.shape.CircleShape,
                    color = Color(0xFFFF4444).copy(alpha = 0.1f),
                    modifier = Modifier.size(36.dp),
                    border = BorderStroke(1.dp, Color(0xFFFF4444).copy(alpha = 0.2f))
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(Icons.Default.Delete, null, tint = Color(0xFFFF4444), modifier = Modifier.size(16.dp))
                    }
                }
            }
        }
    }
}