package com.example.bidcompauction.adminMainActivity.components


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.bidcompauction.adminMainActivity.formatRupiah


@Composable
fun WinnerHighlightCard(
//    winner: BidEntry,
    onConfirm: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1B5E20)),
        modifier = Modifier.fillMaxWidth()
    ) {
//        Row(Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
//            Icon(Icons.Default.EmojiEvents, null, tint = Color.Yellow)
//            Spacer(Modifier.width(12.dp))
//            Column(Modifier.weight(1f)) {
//                Text(winner.userName, fontWeight = FontWeight.Bold)
//                Text(formatRupiah(winner.bidPrice))
//            }
//            Button(onClick = onConfirm) { Text("Confirm") }
//        }
    }
}
