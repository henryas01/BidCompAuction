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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bidcompauction.userMainActivity.UserMainUtils.formatRupiah
import com.example.bidcompauction.userMainActivity.checkout.components.CartItemCard
import com.example.bidcompauction.userMainActivity.myBids.components.PaymentSheetContent
import data.model.AdminFlashSaleResponse // Import model API

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreen(
    cartItems: List<CartItemUi>,
    onBack: () -> Unit,
    onUpdateQty: (String, Int) -> Unit,
    onRemoveItem: (String) -> Unit
) {
    var showPaymentSheet by remember { mutableStateOf(false) }
    val paymentSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val subtotal = cartItems.sumOf { it.price * it.qty }
    val total = subtotal

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Checkout", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color(0xFF0F0F0F))
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF0B0B0B))
                .padding(padding)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    items(cartItems, key = { it.id }) { item ->
                        CartItemCard(
                            item = item,
                            onIncrease = { onUpdateQty(item.id, item.qty + 1) },
                            onDecrease = { if (item.qty > 1) onUpdateQty(item.id, item.qty - 1) },
                            onRemove = { onRemoveItem(item.id) }
                        )
                    }
                }

                Spacer(Modifier.height(16.dp))

                // Voucher Section
                Surface(
                    color = Color(0xFF1A1A1A),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Discount, contentDescription = null, tint = Color.Gray)
                        Spacer(Modifier.width(12.dp))
                        Column {
                            Text("Voucher Code", color = Color.White, fontWeight = FontWeight.Bold)
                            Text("Coming Soon!", color = Color(0xFFFFD700), fontSize = 12.sp)
                        }
                    }
                }

                Spacer(Modifier.height(16.dp))

                // Summary Card
                Surface(
                    color = Color(0xFF1A1A1A),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(Modifier.padding(16.dp)) {
                        SummaryRow("Subtotal", formatRupiah(subtotal))
                        SummaryRow("Shipping", "Free")
                        HorizontalDivider( // Gunakan HorizontalDivider (M3)
                            modifier = Modifier.padding(vertical = 12.dp),
                            color = Color.White.copy(0.1f)
                        )
                        SummaryRow("Total Payment", formatRupiah(total), highlight = true)
                    }
                }

                Spacer(Modifier.height(20.dp))

                // Pay Button
                Button(
                    onClick = { if (cartItems.isNotEmpty()) showPaymentSheet = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    enabled = cartItems.isNotEmpty(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    contentPadding = PaddingValues() // Penting untuk gradient background
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                brush = Brush.horizontalGradient(listOf(Color(0xFFFFD700), Color(0xFFFFA500))),
                                shape = RoundedCornerShape(16.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Payments, null, tint = Color.Black)
                            Spacer(Modifier.width(8.dp))
                            Text("Pay Now", color = Color.Black, fontWeight = FontWeight.ExtraBold)
                        }
                    }
                }
            }
        }

        // --- Payment Bottom Sheet ---
        if (showPaymentSheet) {
            ModalBottomSheet(
                onDismissRequest = { showPaymentSheet = false },
                sheetState = paymentSheetState,
                containerColor = Color(0xFF1A1A1A)
            ) {
                // FIX: Kita bungkus data Checkout ke dalam AdminFlashSaleResponse
                // agar kompatibel dengan PaymentSheetContent yang baru
                val checkoutData = AdminFlashSaleResponse(
                    id = 0,
                    name = "Total Checkout (${cartItems.size} Items)",
                    price = total.toString(),
                    images = emptyList(), // Bisa diisi logo keranjang jika perlu
                    desc = "",
                    isActive= false,
                    stock = 0,
                    startAt = "",
                    isExpired = false,
                    endAt = "",
                )

//                PaymentSheetContent(
//                    bid = checkoutData,
//                    onDismiss = { showPaymentSheet = false }
//                )
            }
        }
    }
}

@Composable
private fun SummaryRow(label: String, value: String, highlight: Boolean = false) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, color = if (highlight) Color.White else Color.Gray)
        Text(
            value,
            color = if (highlight) Color(0xFF00FF85) else Color.White,
            fontWeight = if (highlight) FontWeight.Black else FontWeight.Medium,
            fontSize = if (highlight) 18.sp else 14.sp
        )
    }
}