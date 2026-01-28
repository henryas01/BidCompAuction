package com.example.bidcompauction.adminMainActivity.invoices.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.bidcompauction.adminMainActivity.formatRupiah
import com.example.bidcompauction.adminMainActivity.invoices.AdminInvoice
import data.model.PaymentResponse
import utils.Constants


@Composable
fun PaymentValidationCard(
    payment: PaymentResponse,
    onImageClick: (String) -> Unit,
    onValidate: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1A1A)),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            // Preview Bukti Transfer
            AsyncImage(
                model = payment.proofImages.firstOrNull()?.let { Constants.getFullImageUrl(it) },
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .clickable { payment.proofImages.firstOrNull()?.let { onImageClick(it) } },
                contentScale = ContentScale.Crop
            )

            Spacer(Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(payment.sourceType, color = Color(0xFFFFD700), fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    Spacer(Modifier.width(8.dp))
                    Text("ID: ${payment.sourceId}", color = Color.Gray, fontSize = 12.sp)
                }
                Text(formatRupiah(payment.amount.toLongOrNull() ?: 0L), color = Color.White, fontWeight = FontWeight.Black, fontSize = 18.sp)

                if (payment.status == "PAID") {
                    Text("Inv: ${payment.invoice?.invoiceNumber}", color = Color(0xFF00FF85), fontSize = 11.sp)
                }
            }

            if (payment.status == "PENDING") {
                Button(
                    onClick = onValidate,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00FF85)),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("VALIDATE", color = Color.Black, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                }
            } else {
                Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color(0xFF00FF85))
            }
        }
    }
}