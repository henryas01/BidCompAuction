package com.example.bidcompauction.adminMainActivity.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bidcompauction.adminMainActivity.formatRupiah
import com.example.bidcompauction.adminMainActivity.flashsale.AdminFlashSale
import kotlin.math.max

@Composable
fun FlashSaleItemCard(
    item: AdminFlashSale,
    remainingMs: Long,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    // Logika format waktu sederhana
    val seconds = max(0, (remainingMs / 1000) % 60)
    val minutes = max(0, (remainingMs / (1000 * 60)) % 60)
    val hours = max(0, (remainingMs / (1000 * 60 * 60)))
    val timerText = String.format("%02d:%02d:%02d", hours, minutes, seconds)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1A1A)),
        border = BorderStroke(1.dp, Color(0x1AFFFFFF))
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .height(IntrinsicSize.Min),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Section Gambar
            Box(
                modifier = Modifier
                    .size(90.dp)
                    .clip(RoundedCornerShape(20.dp))
            ) {
                Image(
                    painter = painterResource(item.imageRes),
                    contentDescription = item.name,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                // Badge Timer Flash Sale
                Surface(
                    color = Color(0xFFFFD700),
                    shape = RoundedCornerShape(bottomEnd = 12.dp),
                    modifier = Modifier.align(Alignment.TopStart)
                ) {
                    Text(
                        text = timerText,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Black,
                            color = Color.Black,
                            fontSize = 9.sp
                        )
                    )
                }
            }

            Spacer(Modifier.width(16.dp))

            // Main Content
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = item.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = item.description,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White.copy(alpha = 0.6f),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Text(
                    text = formatRupiah(item.price),
                    style = MaterialTheme.typography.titleSmall,
                    color = Color(0xFF64B5F6), // Biru cerah agar beda dengan produk biasa
                    fontWeight = FontWeight.ExtraBold
                )

                // Stok Badge
                Surface(
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "Sisa: ${item.qty}",
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Spacer(Modifier.width(12.dp))

            // Kolom Aksi (Edit & Delete)
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IconButton(
                    onClick = onEdit,
                    colors = IconButtonDefaults.iconButtonColors(containerColor = Color(0xFF2D2D2D)),
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(Icons.Default.Edit, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
                }

                IconButton(
                    onClick = onDelete,
                    colors = IconButtonDefaults.iconButtonColors(containerColor = Color(0x1AFF4444)),
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(Icons.Default.Delete, contentDescription = null, tint = Color(0xFFFF4444), modifier = Modifier.size(18.dp))
                }
            }
        }
    }
}