package com.example.bidcompauction.userMainActivity.checkout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Discount
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.bidcompauction.R
import com.example.bidcompauction.userMainActivity.UserMainUtils.formatRupiah
import com.example.bidcompauction.userMainActivity.checkout.components.CartItemCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreen(
    onBack: () -> Unit,
    onPay: (Long) -> Unit
) {
    var cartItems by remember {
        mutableStateOf(
            listOf(
                CartItemUi("1", "RTX 3060 Ti", 4_200_000, R.drawable.gpu, 1),
                CartItemUi("2", "SSD NVMe 1TB", 950_000, R.drawable.gpu, 2)
            )
        )
    }

    var voucher by remember { mutableStateOf("") }
    val discountRate = if (voucher.equals("BID10", true)) 0.10 else 0.0

    val subtotal = cartItems.sumOf { it.price * it.qty }
    val discountAmount = (subtotal * discountRate).toLong()
    val total = subtotal - discountAmount

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Checkout") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF0B0B0B))
                .padding(padding)        // ✅ WAJIB
                .padding(16.dp)
        ) {

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(cartItems, key = { it.id }) { item ->
                    CartItemCard(
                        item = item,
                        onIncrease = {
                            cartItems = cartItems.map {
                                if (it.id == item.id) it.copy(qty = it.qty + 1) else it
                            }
                        },
                        onDecrease = {
                            cartItems = cartItems.map {
                                if (it.id == item.id && it.qty > 1) it.copy(qty = it.qty - 1) else it
                            }
                        }
                    )
                }
            }

            Spacer(Modifier.height(14.dp))

            // Voucher
            OutlinedTextField(
                value = voucher,
                onValueChange = { voucher = it },
                leadingIcon = {
                    Icon(Icons.Filled.Discount, contentDescription = "Voucher")
                },
                placeholder = { Text("Voucher code (ex: BID10)") },
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(12.dp))

            // Summary
            SummaryRow("Subtotal", formatRupiah(subtotal))
            SummaryRow("Discount", "- ${formatRupiah(discountAmount)}")
            Divider(color = Color.White.copy(alpha = 0.2f))
            SummaryRow(
                "Total",
                formatRupiah(total),
                highlight = true
            )

            Spacer(Modifier.height(16.dp))

            // Pay button
            Button(
                onClick = { onPay(total) },
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
            ) {
                Icon(Icons.Filled.Payments, contentDescription = "Pay")
                Spacer(Modifier.width(8.dp))
                Text("Pay Now", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun SummaryRow(
    label: String,
    value: String,
    highlight: Boolean = false
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label)
        Text(
            value,
            fontWeight = if (highlight) FontWeight.ExtraBold else FontWeight.Normal,
            color = if (highlight) MaterialTheme.colorScheme.primary else Color.White
        )
    }
}
