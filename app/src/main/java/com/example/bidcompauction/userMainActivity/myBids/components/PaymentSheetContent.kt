package com.example.bidcompauction.userMainActivity.myBids.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bidcompauction.data.model.BidsResponse
import com.example.bidcompauction.userMainActivity.UserMainUtils.formatRupiah
import data.model.AdminFlashSaleResponse

@Composable
fun PaymentSheetContent(
    bid: BidsResponse, // UBAH ke BidsResponse
    onDismiss: () -> Unit
) {
    val amount =formatRupiah(bid.bidPrice.toString().toLongOrNull() ?: 0L)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
            .navigationBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Payment Instructions",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = bid.flashSale?.name ?: "Auction Product",
            color = Color.Gray,
            fontSize = 14.sp
        )

        Spacer(Modifier.height(24.dp))

        // Info Rekening
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF252525)),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text("Transfer to Bank BCA", color = Color.Gray, fontSize = 12.sp)
                Text("8830 1234 567", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Black)
                Text("a/n PT BID COMP AUCTION", color = Color.White, fontSize = 14.sp)

                // Material3 menggunakan HorizontalDivider sebagai pengganti Divider
                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 16.dp),
                    color = Color.DarkGray,
                    thickness = 0.5.dp
                )

                Text("Total Amount to Pay", color = Color.Gray, fontSize = 12.sp)
                Text(
                    text = amount,
                    color = Color(0xFF00FF85),
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Black
                )
            }
        }

        Spacer(Modifier.height(32.dp))

        // Simulasi Upload Bukti
        OutlinedButton(
            onClick = { /* Simulasi pilih file dari gallery */ },
            modifier = Modifier.fillMaxWidth().height(50.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
            border = ButtonDefaults.outlinedButtonBorder.copy(brush = androidx.compose.ui.graphics.SolidColor(Color.DarkGray))
        ) {
            Text("Select Payment Proof Image")
        }

        Spacer(Modifier.height(12.dp))

        // Tombol Konfirmasi Final
        Button(
            onClick = {
                // TODO: Kirim data bukti transfer ke API Admin
                onDismiss()
            },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFD700)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = "CONFIRM PAYMENT",
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }
    }
}