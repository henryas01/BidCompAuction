package com.example.bidcompauction.userMainActivity.notification

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.bidcompauction.userMainActivity.notification.components.NotificationCard

private enum class NotifFilter { All, Unread }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationScreen(
    onBack: () -> Unit,
    onOpenNotification: (NotificationUi) -> Unit
) {
    var filter by remember { mutableStateOf(NotifFilter.All) }
    var query by remember { mutableStateOf("") }

    // Dummy data (ganti nanti dari API/DB)
    var notifications by remember {
        mutableStateOf(
            listOf(
                NotificationUi(
                    id = "n1",
                    title = "Payment Success",
                    message = "Invoice INV-001 sudah berhasil dibayar.",
                    time = "Today • 10:21",
                    unread = true,
                    type = NotificationType.ORDER
                ),
                NotificationUi(
                    id = "n2",
                    title = "Flash Sale!",
                    message = "Diskon sampai 20% untuk SSD NVMe 2TB. Waktu terbatas.",
                    time = "Today • 09:05",
                    unread = true,
                    type = NotificationType.PROMO
                ),
                NotificationUi(
                    id = "n3",
                    title = "Security Update",
                    message = "Kami meningkatkan keamanan akunmu. Silakan update password jika perlu.",
                    time = "Yesterday • 18:30",
                    unread = false,
                    type = NotificationType.SYSTEM
                )
            )
        )
    }

    val unreadCount = notifications.count { it.unread }

    val filtered = remember(notifications, filter, query) {
        val q = query.trim().lowercase()
        notifications
            .asSequence()
            .filter { if (filter == NotifFilter.Unread) it.unread else true }
            .filter { if (q.isBlank()) true else (it.title.lowercase().contains(q) || it.message.lowercase().contains(q)) }
            .toList()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("Notifications", fontWeight = FontWeight.Bold)
                        Text(
                            if (unreadCount > 0) "$unreadCount unread" else "All caught up",
                            style = MaterialTheme.typography.labelMedium,
                            color = Color.White.copy(alpha = 0.7f)
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            // mark all as read
                            notifications = notifications.map { it.copy(unread = false) }
                        },
                        enabled = unreadCount > 0
                    ) {
                        Icon(Icons.Filled.DoneAll, contentDescription = "Mark all read")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(
                    Brush.verticalGradient(
                        listOf(Color(0xFF0B0B0B), Color(0xFF0F0F0F), Color(0xFF0B0B0B))
                    )
                )
                .padding(16.dp)
        ) {
            // Search
            TextField(
                value = query,
                onValueChange = { query = it },
                singleLine = true,
                leadingIcon = {
                    Icon(Icons.Filled.Search, contentDescription = "Search", tint = Color(0xFF3A3A3A))
                },
                placeholder = { Text("Search notification...", color = Color(0xFF8A8A8A)) },
                shape = RoundedCornerShape(18.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedTextColor = Color(0xFF111111),
                    unfocusedTextColor = Color(0xFF111111),
                    cursorColor = MaterialTheme.colorScheme.primary,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
            )

            Spacer(Modifier.height(12.dp))

            // Filter chips
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0x22FFFFFF))
                    .padding(6.dp)
            ) {
                FilterChipItem(
                    text = "All",
                    selected = filter == NotifFilter.All,
                    onClick = { filter = NotifFilter.All }
                )
                Spacer(Modifier.width(8.dp))
                FilterChipItem(
                    text = "Unread",
                    selected = filter == NotifFilter.Unread,
                    onClick = { filter = NotifFilter.Unread }
                )
            }

            Spacer(Modifier.height(12.dp))

            if (filtered.isEmpty()) {
                Box(Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                    Text("No notifications", color = Color.White.copy(alpha = 0.75f))
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    contentPadding = PaddingValues(bottom = 16.dp)
                ) {
                    items(filtered, key = { it.id }) { item ->
                        NotificationCard(
                            item = item,
                            onClick = { onOpenNotification(item) },
                            onMarkRead = if (item.unread) {
                                {
                                    notifications = notifications.map {
                                        if (it.id == item.id) it.copy(unread = false) else it
                                    }
                                }
                            } else null
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun FilterChipItem(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    val bg = if (selected) MaterialTheme.colorScheme.primary else Color.Transparent
    val fg = if (selected) MaterialTheme.colorScheme.onPrimary else Color.White

    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(14.dp),
        color = bg
    ) {
        Text(
            text = text,
            color = fg,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
        )
    }
}
