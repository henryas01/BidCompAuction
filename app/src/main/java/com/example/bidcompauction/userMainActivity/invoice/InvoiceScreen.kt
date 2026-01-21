package com.example.bidcompauction.userMainActivity.invoice

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.bidcompauction.userMainActivity.invoice.components.InvoiceCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InvoiceScreen(
    onBack: () -> Unit,
    onInvoiceClick: (InvoiceUi) -> Unit
) {
    // Dummy data (nanti ganti dari API)
    val invoices = listOf(
        InvoiceUi("INV-001", "12 Jan 2026", 5_150_000, InvoiceStatus.PAID),
        InvoiceUi("INV-002", "14 Jan 2026", 2_300_000, InvoiceStatus.UNPAID),
        InvoiceUi("INV-003", "01 Jan 2026", 1_750_000, InvoiceStatus.EXPIRED)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Invoice") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF0B0B0B))
                .padding(padding)
                .padding(16.dp)
        ) {

            Text(
                "Your Invoices",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(12.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(invoices, key = { it.id }) { invoice ->
                    InvoiceCard(
                        invoice = invoice,
                        onClick = { onInvoiceClick(invoice) }
                    )
                }
            }
        }
    }
}
