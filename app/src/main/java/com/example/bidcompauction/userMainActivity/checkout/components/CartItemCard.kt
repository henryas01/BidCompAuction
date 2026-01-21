package com.example.bidcompauction.userMainActivity.checkout.components

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
import com.example.bidcompauction.userMainActivity.UserMainUtils.formatRupiah
import com.example.bidcompauction.userMainActivity.checkout.CartItemUi

@Composable
fun CartItemCard(
    item: CartItemUi,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF101010)),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(70.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(Color(0xFF0D0D0D))
            ) {
                Image(
                    painter = painterResource(id = item.imageRes),
                    contentDescription = item.name,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.fillMaxSize().padding(8.dp)
                )
            }

            Spacer(Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(item.name, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(4.dp))
                Text(
                    formatRupiah(item.price),
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onDecrease,
                    enabled = item.qty > 1
                ) {
                    Icon(Icons.Filled.Remove, contentDescription = "Decrease")
                }

                Text(
                    text = item.qty.toString(),
                    modifier = Modifier.width(24.dp),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )

                IconButton(onClick = onIncrease) {
                    Icon(Icons.Filled.Add, contentDescription = "Increase")
                }
            }
        }
    }
}
