package com.example.bidcompauction.userMainActivity.invoice.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bidcompauction.userMainActivity.UserMainUtils.formatRupiah
import com.example.bidcompauction.userMainActivity.invoice.InvoiceStatus
import com.example.bidcompauction.userMainActivity.invoice.InvoiceUi
import com.example.bidcompauction.userMainActivity.invoice.TransactionType

@Composable
fun InvoiceCard(
    invoice: InvoiceUi,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1A1A)),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            // Baris Atas: ID & Tipe Produk
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = invoice.id,
                    color = Color.Gray,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )

                // Label Tipe: BID atau DIRECT
                Surface(
                    color = if (invoice.type == TransactionType.BID)
                        Color(0xFFFFD700).copy(alpha = 0.1f)
                    else Color(0xFF00B0FF).copy(alpha = 0.1f),
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text(
                        text = if (invoice.type == TransactionType.BID) "BID WINNER" else "DIRECT BUY",
                        color = if (invoice.type == TransactionType.BID) Color(0xFFFFD700) else Color(0xFF00B0FF),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Black
                    )
                }
            }

            Spacer(Modifier.height(8.dp))

            // Nama Produk (Highlight Utama)
            Text(
                text = invoice.productName,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = "Purchased on ${invoice.date}",
                color = Color.Gray,
                fontSize = 12.sp
            )

            Spacer(Modifier.height(16.dp))
            Divider(color = Color.White.copy(alpha = 0.05f))
            Spacer(Modifier.height(16.dp))

            // Baris Bawah: Status Lunas & Total
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = Color(0xFF00FF85),
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(Modifier.width(6.dp))
                    Text("Paid Success", color = Color(0xFF00FF85), fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }

                Text(
                    text = formatRupiah(invoice.total),
                    color = Color.White,
                    fontWeight = FontWeight.Black,
                    fontSize = 18.sp
                )
            }
        }
    }
}
