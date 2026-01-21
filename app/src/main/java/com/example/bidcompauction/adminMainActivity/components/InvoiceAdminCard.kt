package com.example.bidcompauction.adminMainActivity.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.bidcompauction.adminMainActivity.formatRupiah


@Composable
fun InvoiceAdminCard(
//    invoice: InvoiceAdmin,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        colors = CardDefaults.cardColors(containerColor = Color(0xFF101010)),
        modifier = Modifier.fillMaxWidth()
    ) {
//        Column(Modifier.padding(14.dp)) {
//            Text("Invoice #${invoice.id}", fontWeight = FontWeight.Bold)
//            Text(invoice.userName, color = Color.White.copy(0.7f))
//            Text(formatRupiah(invoice.total), color = MaterialTheme.colorScheme.primary)
//        }
    }
}
