package com.example.bidcompauction.userMainActivity.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.bidcompauction.userMainActivity.FlashSaleUi
import androidx.compose.runtime.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp
import com.example.bidcompauction.userMainActivity.components.*
import data.model.AdminFlashSaleResponse



@Composable
fun BidBottomSheetContent(
    item: AdminFlashSaleResponse,
    onConfirmBid: (Long) -> Unit
) {
    // Ambil harga dasar dari API secara aman
    val basePrice = remember(item.price) {
        item.price.toString().toLongOrNull() ?: 0L
    }

    // State untuk jumlah bid (Default: harga saat ini + 50rb)
    var bidAmount by remember { mutableLongStateOf(basePrice + 50_000) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
            .navigationBarsPadding()
    ) {
        Text(
            text = "Place Your Bid",
            style = MaterialTheme.typography.headlineSmall,
            color = Color.White
        )
        Text(
            text = item.name,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(24.dp))

        // UI Harga Saat Ini
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Current Min Bid", color = Color.Gray)
            Text(
                "Rp ${String.format("%,d", basePrice)}",
                color = Color(0xFF00FF85),
                style = MaterialTheme.typography.titleMedium
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Input Harga Bid
        OutlinedTextField(
            value = bidAmount.toString(),
            onValueChange = {
                // Mencegah input kosong atau non-angka
                bidAmount = it.toLongOrNull() ?: 0L
            },
            label = { Text("Your Bid Amount") },
            prefix = { Text("Rp ") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedBorderColor = Color(0xFFFFD700),
                cursorColor = Color(0xFFFFD700),
                focusedLabelColor = Color(0xFFFFD700)
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        // Quick Bid Buttons
        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf(10_000, 50_000, 100_000).forEach { increment ->
                AssistChip(
                    onClick = { bidAmount += increment },
                    label = { Text("+${String.format("%,d", increment)}", color = Color.White) },
                    colors = AssistChipDefaults.assistChipColors(containerColor = Color(0xFF1F1F1F))
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Error message jika bid terlalu rendah
        if (bidAmount <= basePrice && bidAmount != 0L) {
            Text(
                text = "Bid must be higher than current price",
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        // Confirm Button
        Button(
            onClick = { onConfirmBid(bidAmount) },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFFD700),
                disabledContainerColor = Color.Gray
            ),
            // Tombol aktif hanya jika bid lebih tinggi dari harga awal
            enabled = bidAmount > basePrice
        ) {
            Text("PLACE BID", color = Color.Black, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        }
    }
}