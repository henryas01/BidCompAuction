package com.example.bidcompauction.adminMainActivity.products.components

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import data.model.AdminProductResponse
import utils.Constants

@Composable
fun ItemCard(
    product: AdminProductResponse, // Menambahkan koma yang hilang
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    // Alamat dasar server untuk gambar
//    val baseUrl = "http://192.168.1.10:3000"
//    val imageUrl = if (product.images.isNotEmpty()) "$baseUrl${product.images.first()}" else ""
    val imageUrl = Constants.getFullImageUrl(product.images.firstOrNull())
//    print("product.images, $product.images")
    Log.d("imageUrl = imageUrl", "URL Gambar: $imageUrl")
    Log.d("imageUrl = ItemCard", "URL Gambar: $product.images")

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(135.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF141414)),
        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.08f))
    ) {
        Row(modifier = Modifier.fillMaxSize()) {

            Box(
                modifier = Modifier
                    .width(115.dp)
                    .fillMaxHeight()
            ) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = product.name,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(R.drawable.ic_placeholder),
                    error = painterResource(R.drawable.ic_placeholder)
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(Color.Transparent, Color(0xFF141414)),
                                startX = 0f
                            )
                        )
                )
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
                    text = product.name,
                    color = Color.White,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 16.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = product.desc, // Diubah dari .description ke .desc
                    color = Color.White.copy(alpha = 0.5f),
                    fontSize = 10.sp,
                    lineHeight = 13.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(vertical = 4.dp)
                )

                // Harga (Mengonversi 'Any' ke Long/String untuk formatRupiah)
                val priceLong = when(val p = product.price) {
                    is Number -> p.toLong()
                    is String -> p.toLongOrNull() ?: 0L
                    else -> 0L
                }

                Text(
                    text = formatRupiah(priceLong),
                    color = Color(0xFF00FF85),
                    fontWeight = FontWeight.Black,
                    fontSize = 15.sp,
                    letterSpacing = (-0.5).sp
                )

                Spacer(Modifier.height(8.dp))

                // Badge Stok (Diubah dari .qty ke .stock)
                Surface(
                    color = if (product.stock > 5) Color(0xFF00FF85).copy(0.12f) else Color(0xFFFF4444).copy(0.12f),
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(0.5.dp, if (product.stock > 5) Color(0xFF00FF85).copy(0.3f) else Color(0xFFFF4444).copy(0.3f))
                ) {
                    Text(
                        text = "STOCK: ${product.stock}",
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                        color = if (product.stock > 5) Color(0xFF00FF85) else Color(0xFFFF4444),
                        fontSize = 9.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            }

            // --- SISI KANAN: ACTION BUTTONS ---
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
                    shape = CircleShape,
                    color = Color(0xFF222222),
                    modifier = Modifier.size(36.dp),
                    border = BorderStroke(1.dp, Color.White.copy(alpha = 0.1f))
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            Icons.Default.Edit,
                            null,
                            tint = Color(0xFFFFD700),
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }

                Surface(
                    onClick = onDelete,
                    shape = CircleShape,
                    color = Color(0xFFFF4444).copy(alpha = 0.1f),
                    modifier = Modifier.size(36.dp),
                    border = BorderStroke(1.dp, Color(0xFFFF4444).copy(alpha = 0.2f))
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            Icons.Default.Delete,
                            null,
                            tint = Color(0xFFFF4444),
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }
    }
}