package com.example.bidcompauction.userMainActivity.invoice

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bidcompauction.userMainActivity.invoice.components.InvoiceCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InvoiceScreen(
    onBack: () -> Unit,
    onInvoiceClick: (InvoiceUi) -> Unit
) {
    // Data difilter: Hanya menampilkan yang sudah SUCCESS / PAID
    val successInvoices = listOf(
        InvoiceUi(
            "INV-8821", "24 Jan 2026", "RTX 4070 SUPER",
            10_500_000, InvoiceStatus.PAID, TransactionType.BID
        ),
        InvoiceUi(
            "INV-9012", "23 Jan 2026", "Mechanical Keyboard K8",
            1_200_000, InvoiceStatus.PAID, TransactionType.DIRECT
        ),
        InvoiceUi(
            "INV-7712", "20 Jan 2026", "SSD NVMe 1TB Gen4",
            1_450_000, InvoiceStatus.PAID, TransactionType.DIRECT
        )
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Success Transactions", color = Color.White, fontSize = 18.sp) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color(0xFF0F0F0F))
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF0B0B0B))
                .padding(padding)
        ) {
            if (successInvoices.isEmpty()) {
                EmptyInvoiceState()
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(successInvoices) { invoice ->
                        InvoiceCard(
                            invoice = invoice,
                            onClick = { onInvoiceClick(invoice) }
                        )
                    }
                }
            }
        }
    }
}
@Composable
fun EmptyInvoiceState() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(Icons.Default.Receipt, null, tint = Color.DarkGray, modifier = Modifier.size(64.dp))
        Spacer(Modifier.height(16.dp))
        Text("No transactions yet", color = Color.Gray)
    }
}