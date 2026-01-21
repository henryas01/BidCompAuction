package com.example.bidcompauction.userMainActivity.notification.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.bidcompauction.userMainActivity.notification.NotificationType
import com.example.bidcompauction.userMainActivity.notification.NotificationUi

@Composable
fun NotificationCard(
    item: NotificationUi,
    onClick: () -> Unit,
    onMarkRead: (() -> Unit)? = null
) {
    val icon: ImageVector = when (item.type) {
        NotificationType.ORDER -> Icons.Filled.ReceiptLong
        NotificationType.PROMO -> Icons.Filled.LocalOffer
        NotificationType.SYSTEM -> Icons.Filled.Settings
    }

    val container = if (item.unread) Color(0xFF141414) else Color(0xFF101010)
    val border = if (item.unread) Color(0x33FFC107) else Color.Transparent

    Card(
        onClick = onClick,
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = container),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.Top
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(Color(0x22FFFFFF)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
            }

            Spacer(Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = item.title,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    if (item.unread) {
                        Spacer(Modifier.width(8.dp))
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .background(Color(0xFFFF3D00))
                                .padding(horizontal = 8.dp, vertical = 3.dp)
                        ) {
                            Text(
                                text = "NEW",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                Spacer(Modifier.height(4.dp))

                Text(
                    text = item.message,
                    color = Color.White.copy(alpha = 0.75f),
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = item.time,
                        color = Color.White.copy(alpha = 0.55f),
                        style = MaterialTheme.typography.labelMedium
                    )

                    if (item.unread && onMarkRead != null) {
                        TextButton(onClick = onMarkRead, contentPadding = PaddingValues(0.dp)) {
                            Text(
                                "Mark read",
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }
        }

        if (border != Color.Transparent) {
            Divider(color = border)
        }
    }
}
