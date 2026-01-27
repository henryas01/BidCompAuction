package com.example.bidcompauction.userMainActivity.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.bidcompauction.R
import com.example.bidcompauction.userMainActivity.UserMainUtils.formatRupiah
import data.model.AdminProductResponse
import utils.Constants


@Composable
fun ProductCard(
    p: AdminProductResponse,
    onAdd: () -> Unit
) {
    val imageUrl = Constants.getFullImageUrl(p.images.firstOrNull())

    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF101010)),
        modifier = Modifier.fillMaxWidth().height(260.dp)
    ) {
        Column(Modifier.fillMaxSize().padding(12.dp)) {
            Box(
                modifier = Modifier.fillMaxWidth().height(140.dp)
                    .clip(RoundedCornerShape(18.dp)).background(Color(0xFF0D0D0D))
            ) {
                // Ganti Image ke AsyncImage Coil
                AsyncImage(
                    model = imageUrl,
                    contentDescription = p.name,
                    placeholder = painterResource(R.drawable.ic_placeholder),
                    error = painterResource(R.drawable.ic_placeholder),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                Box(
                    modifier = Modifier.align(Alignment.TopEnd).padding(10.dp)
                        .clip(RoundedCornerShape(14.dp)).background(Color.Black.copy(0.5f))
                        .padding(horizontal = 10.dp, vertical = 6.dp)
                ) {
                    Text("Qty ${p.stock}", color = Color.White, fontSize = 10.sp)
                }
            }

            Spacer(Modifier.height(10.dp))
            Text(p.name, fontWeight = FontWeight.Bold, maxLines = 1, overflow = TextOverflow.Ellipsis)

            // Konversi harga Any ke Long
            val priceLong = when(val pr = p.price) {
                is Number -> pr.toLong()
                is String -> pr.toLongOrNull() ?: 0L
                else -> 0L
            }
            Text(formatRupiah(priceLong), color = Color(0xFFFFD700), fontWeight = FontWeight.ExtraBold)

            Spacer(Modifier.weight(1f))

            Button(
                onClick = onAdd, // Navigasi ke Checkout
                enabled = p.stock > 0,
                modifier = Modifier.fillMaxWidth().height(44.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFD700))
            ) {
                Icon(Icons.Filled.AddShoppingCart, null, modifier = Modifier.size(18.dp), tint = Color.Black)
                Spacer(Modifier.width(8.dp))
                Text("Add", color = Color.Black, fontWeight = FontWeight.Bold)
            }
        }
    }
}