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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.example.bidcompauction.adminMainActivity.invoices.components.PaymentValidationCard
import data.viewmodel.PaymentValidationViewModel
import utils.Constants

//
//@Composable
//fun InvoicesScreen(search: String) {
//    val allInvoices = remember {
//        listOf(
//            AdminInvoice("INV-8821", "Rizky Pratama", "RTX 4070 SUPER", 10500000, "24 Jan, 14:20"),
//            AdminInvoice("INV-8822", "Siti Aminah", "Logitech G Pro X", 1200000, "24 Jan, 15:10"),
//            AdminInvoice("INV-8823", "Budi Santoso", "Monitor Odyssey G7", 7500000, "23 Jan, 09:45"),
//            AdminInvoice("INV-8824", "Andi Wijaya", "Keychron K2 V2", 1500000, "22 Jan, 11:30")
//        )
//    }
//
//    LazyColumn(
//        modifier = Modifier.fillMaxSize(),
//        contentPadding = PaddingValues(16.dp),
//        verticalArrangement = Arrangement.spacedBy(12.dp)
//    ) {
//        val filteredInvoices = allInvoices.filter {
//            it.customerName.contains(search, true) || it.id.contains(search, true)
//        }
//
//        items(filteredInvoices) { invoice ->
//            InvoiceAdminCard(invoice = invoice)
//        }
//    }
//}

@Composable
fun InvoicesScreen(
    search: String,
    viewModel: PaymentValidationViewModel = viewModel()
) {
    val payments by viewModel.payments.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    var selectedImage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        viewModel.fetchAllPayments()
    }

    Column(modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)) {
        Text(
            "Payment Validation",
            style = MaterialTheme.typography.headlineSmall,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally), color = Color(0xFFFFD700))
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                val filtered = payments.filter { it.status.contains(search, true) || it.sourceType.contains(search, true) }

                items(filtered) { payment ->
                    PaymentValidationCard(
                        payment = payment,
                        onImageClick = { selectedImage = it },
                        onValidate = { viewModel.validatePayment(payment.id) }
                    )
                }
            }
        }
    }

    // Dialog untuk zoom bukti transfer
    if (selectedImage != null) {
        AlertDialog(
            onDismissRequest = { selectedImage = null },
            confirmButton = {},
            containerColor = Color.Transparent,
            text = {
                AsyncImage(
                    model = Constants.getFullImageUrl(selectedImage!!),
                    contentDescription = "Proof",
                    modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.FillWidth
                )
            }
        )
    }
}
