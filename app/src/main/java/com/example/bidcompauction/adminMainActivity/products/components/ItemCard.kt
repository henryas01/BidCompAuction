package com.example.bidcompauction.adminMainActivity.products.components

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
import com.example.bidcompauction.adminMainActivity.formatRupiah
import com.example.bidcompauction.adminMainActivity.products.AdminProduct
@Composable
fun ItemCard(
    product: AdminProduct,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp), // Memberi sedikit ruang napas
        shape = RoundedCornerShape(24.dp), // Lebih bulat agar modern
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1A1A)),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp), // Flat design seringkali lebih bersih
        border = BorderStroke(1.dp, Color(0x1AFFFFFF)) // Border tipis untuk depth
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .height(IntrinsicSize.Min), // Menyeimbangkan tinggi konten
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Image dengan shadow/glow tipis
            Box(
                modifier = Modifier
                    .size(90.dp)
                    .clip(RoundedCornerShape(20.dp))
            ) {
                Image(
                    painter = painterResource(product.imageRes),
                    contentDescription = product.name,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(Modifier.width(16.dp))

            // Main content
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = product.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = product.description,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White.copy(alpha = 0.6f),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = formatRupiah(product.price),
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.primaryContainer,
                        fontWeight = FontWeight.ExtraBold
                    )


                }
                // Badge Stock yang lebih minimalis
                Surface(
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "Stok: ${product.qty}",
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Spacer(Modifier.width(12.dp))

            // Vertical Actions (Lebih ringkas)
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                IconButton(
                    onClick = onEdit,
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = Color(0xFF2D2D2D)
                    ),
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        Icons.Default.Edit,
                        contentDescription = "Edit",
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                }

                IconButton(
                    onClick = onDelete,
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = Color(0x1AFF4444)
                    ),
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = Color(0xFFFF4444),
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}