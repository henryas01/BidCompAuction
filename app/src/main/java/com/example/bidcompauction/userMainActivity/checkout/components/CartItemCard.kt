package com.example.bidcompauction.userMainActivity.checkout.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
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
import com.example.bidcompauction.userMainActivity.UserMainUtils.formatRupiah
import com.example.bidcompauction.userMainActivity.checkout.CartItemUi
import utils.Constants
import com.example.bidcompauction.R
import coil3.compose.AsyncImage

import androidx.compose.material.icons.filled.DeleteOutline



import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.ui.text.style.TextOverflow

@Composable
fun CartItemCard(
    item: CartItemUi,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    onRemove: () -> Unit
){
    val imageUrl = Constants.getFullImageUrl(item.imageUrl)

    Surface(
        color = Color(0xFF1A1A1A),
        shape = RoundedCornerShape(20.dp), // Radius lebih lembut
        border = BorderStroke(1.dp, Color(0xFF252525)), // Outline tipis agar lebih mewah
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // --- IMAGE ---
            Box(
                modifier = Modifier
                    .size(85.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFF101010))
            ) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                    error = painterResource(R.drawable.ic_placeholder)
                )
            }

            Spacer(Modifier.width(16.dp))

            // --- INFO (Nama & Harga) ---
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.name,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(Modifier.height(4.dp))

                Text(
                    text = formatRupiah(item.price),
                    color = Color(0xFFFFD700), // Warna Gold Auction
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 14.sp
                )
            }

            // --- ACTIONS (Qty & Delete) ---
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Tombol Hapus Kecil di Atas
                IconButton(
                    onClick = onRemove,
                    modifier = Modifier.size(28.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.DeleteSweep,
                        contentDescription = "Remove",
                        tint = Color(0xFFFF5252).copy(alpha = 0.8f),
                        modifier = Modifier.size(20.dp)
                    )
                }

                // Quantity Control yang lebih Slim
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .background(Color(0xFF252525), RoundedCornerShape(10.dp))
                        .padding(horizontal = 2.dp)
                ) {
                    IconButton(onClick = onDecrease, modifier = Modifier.size(30.dp)) {
                        Icon(Icons.Default.Remove, null, tint = Color.White, modifier = Modifier.size(14.dp))
                    }

                    Text(
                        text = item.qty.toString(),
                        color = Color.White,
                        fontWeight = FontWeight.Black,
                        fontSize = 13.sp,
                        modifier = Modifier.padding(horizontal = 4.dp)
                    )

                    IconButton(onClick = onIncrease, modifier = Modifier.size(30.dp)) {
                        Icon(Icons.Default.Add, null, tint = Color.White, modifier = Modifier.size(14.dp))
                    }
                }
            }
        }
    }
}