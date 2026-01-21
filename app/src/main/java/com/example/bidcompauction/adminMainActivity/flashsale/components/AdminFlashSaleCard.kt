package com.example.bidcompauction.adminMainActivity.flashsale.components

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
import androidx.compose.ui.unit.dp
import com.example.bidcompauction.adminMainActivity.flashsale.AdminFlashSale
import com.example.bidcompauction.adminMainActivity.formatCountdown
import com.example.bidcompauction.adminMainActivity.formatRupiah

@Composable
fun AdminFlashSaleCard(
    item: AdminFlashSale,
    remainingMs: Long,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF101010))
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                painter = painterResource(item.imageRes),
                contentDescription = item.name,
                modifier = Modifier
                    .size(84.dp)
                    .clip(RoundedCornerShape(14.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(item.name, fontWeight = FontWeight.Bold)
                Text(
                    item.description,
                    color = Color.White.copy(alpha = 0.7f),
                    maxLines = 2
                )

                Spacer(Modifier.height(8.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    AssistChip(
                        onClick = {},
                        label = { Text("Qty ${item.qty}") }
                    )
                    AssistChip(
                        onClick = {},
                        label = { Text(formatRupiah(item.price)) },
                        colors = AssistChipDefaults.assistChipColors(
                            labelColor = MaterialTheme.colorScheme.primary
                        )
                    )
                }

                Spacer(Modifier.height(6.dp))

                AssistChip(
                    onClick = {},
                    label = {
                        Text(
                            if (remainingMs > 0)
                                "⏱ ${formatCountdown(remainingMs)}"
                            else
                                "ENDED"
                        )
                    },
                    colors = AssistChipDefaults.assistChipColors(
                        containerColor =
                            if (remainingMs > 0) Color(0x22FFFFFF)
                            else Color(0x33FF6B6B),
                        labelColor = Color.White
                    )
                )
            }

            Column(horizontalAlignment = Alignment.End) {
                IconButton(onClick = onEdit) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit")
                }
                IconButton(onClick = onDelete) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = Color(0xFFFF6B6B)
                    )
                }
            }
        }
    }
}
