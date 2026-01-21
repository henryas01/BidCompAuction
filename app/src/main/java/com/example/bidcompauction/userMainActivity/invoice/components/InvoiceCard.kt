package com.example.bidcompauction.userMainActivity.invoice.components

import androidx.compose.ui.Alignment
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.bidcompauction.userMainActivity.UserMainUtils.formatRupiah
import com.example.bidcompauction.userMainActivity.invoice.InvoiceStatus
import com.example.bidcompauction.userMainActivity.invoice.InvoiceUi

@Composable
fun InvoiceCard(
    invoice: InvoiceUi,
    onClick: () -> Unit
) {
    val (bg, textColor) = when (invoice.status) {
        InvoiceStatus.PAID -> Color(0xFF1B5E20) to Color(0xFFA5D6A7)
        InvoiceStatus.UNPAID -> Color(0xFF4E342E) to Color(0xFFFFCC80)
        InvoiceStatus.EXPIRED -> Color(0xFF263238) to Color(0xFF90A4AE)
    }

    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF101010)),
        elevation = CardDefaults.cardElevation(6.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(Modifier.padding(14.dp)) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "Invoice #${invoice.id}",
                    fontWeight = FontWeight.Bold
                )
                Box(
                    modifier = Modifier
                        .background(bg, RoundedCornerShape(12.dp))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        invoice.status.name,
                        color = textColor,
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(Modifier.height(6.dp))

            Text(
                invoice.date,
                style = MaterialTheme.typography.labelMedium,
                color = Color.White.copy(alpha = 0.7f)
            )

            Spacer(Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "Total",
                    color = Color.White.copy(alpha = 0.7f)
                )
                Text(
                    formatRupiah(invoice.total),
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.ExtraBold
                )
            }

            Spacer(Modifier.height(10.dp))

            TextButton(
                onClick = onClick,
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("View Detail")
            }
        }
    }
}
