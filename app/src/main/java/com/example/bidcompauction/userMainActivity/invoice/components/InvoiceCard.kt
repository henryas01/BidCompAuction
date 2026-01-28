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
import data.model.UserPaymentInvoiceResponse

@Composable
fun InvoiceCard(
    data: UserPaymentInvoiceResponse,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1A1A)),
        modifier = Modifier.fillMaxWidth().clickable { onClick() }
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Menampilkan Invoice Number dari nested object
                Text(
                    text = data.invoice?.invoiceNumber ?: "Processing...",
                    color = Color.Gray,
                    fontSize = 12.sp
                )

                Surface(
                    color = if (data.sourceType == "BID") Color(0xFFFFD700).copy(0.1f) else Color(0xFF00B0FF).copy(0.1f),
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text(
                        text = if (data.sourceType == "BID") "BID WINNER" else "DIRECT BUY",
                        color = if (data.sourceType == "BID") Color(0xFFFFD700) else Color(0xFF00B0FF),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Black
                    )
                }
            }

            Spacer(Modifier.height(8.dp))

            // Nama produk bisa diambil dari field lain atau default text jika tidak tersedia di endpoint ini
            Text(
                text = "Transaction #${data.id}",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp
            )

            Text(
                text = "Paid on ${data.createdAt.take(10)}", // Ambil tanggal saja YYYY-MM-DD
                color = Color.Gray,
                fontSize = 12.sp
            )

            Spacer(Modifier.height(16.dp))
            HorizontalDivider(color = Color.White.copy(alpha = 0.05f))
            Spacer(Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.CheckCircle, null, tint = Color(0xFF00FF85), modifier = Modifier.size(16.dp))
                    Spacer(Modifier.width(6.dp))
                    Text("PAID", color = Color(0xFF00FF85), fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }

                Text(
                    text = formatRupiah(data.amount.toLongOrNull() ?: 0L),
                    color = Color.White,
                    fontWeight = FontWeight.Black,
                    fontSize = 18.sp
                )
            }
        }
    }
}