package com.example.bidcompauction.adminMainActivity.components


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.bidcompauction.adminMainActivity.formatRupiah


@Composable
fun BidRowCard(
//    entry: BidEntry,
    isTop: Boolean,
    onPick: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0xFF101010)),
        modifier = Modifier.fillMaxWidth()
    ) {
//        Row(Modifier.padding(12.dp), horizontalArrangement = Arrangement.SpaceBetween) {
//            Column {
//                Text("${entry.userName} (${entry.userId})", fontWeight = FontWeight.Bold)
//                Text(entry.time, color = Color.White.copy(0.6f))
//            }
//            Column(horizontalAlignment = Alignment.End) {
//                Text(formatRupiah(entry.bidPrice), color = MaterialTheme.colorScheme.primary)
//                TextButton(onClick = onPick) {
//                    Text(if (isTop) "Winner" else "Select")
//                }
//            }
//        }
    }
}
