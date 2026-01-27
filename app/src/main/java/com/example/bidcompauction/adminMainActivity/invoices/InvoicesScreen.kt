package com.example.bidcompauction.adminMainActivity.invoices

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import com.example.bidcompauction.adminMainActivity.invoices.components.InvoiceAdminCard
import androidx.compose.foundation.lazy.items


@Composable
fun InvoicesScreen(search: String) {
    val allInvoices = remember {
        listOf(
            AdminInvoice("INV-8821", "Rizky Pratama", "RTX 4070 SUPER", 10500000, "24 Jan, 14:20"),
            AdminInvoice("INV-8822", "Siti Aminah", "Logitech G Pro X", 1200000, "24 Jan, 15:10"),
            AdminInvoice("INV-8823", "Budi Santoso", "Monitor Odyssey G7", 7500000, "23 Jan, 09:45"),
            AdminInvoice("INV-8824", "Andi Wijaya", "Keychron K2 V2", 1500000, "22 Jan, 11:30")
        )
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        val filteredInvoices = allInvoices.filter {
            it.customerName.contains(search, true) || it.id.contains(search, true)
        }

        items(filteredInvoices) { invoice ->
            InvoiceAdminCard(invoice = invoice)
        }
    }
}
