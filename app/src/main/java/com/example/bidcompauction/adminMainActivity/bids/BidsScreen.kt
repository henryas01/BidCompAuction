package com.example.bidcompauction.adminMainActivity.bids

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bidcompauction.adminMainActivity.bids.components.BidUserCard
import com.example.bidcompauction.adminMainActivity.formatRupiah
import com.example.bidcompauction.data.model.BidsResponse
import data.viewmodel.BidsViewModel


@Composable
fun BidsScreen(
    search: String,
    viewModel: BidsViewModel = viewModel()
) {
    val allBids by viewModel.allBids.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    var selectedWinner by remember { mutableStateOf<BidsResponse?>(null) }

    // Ambil data saat pertama kali dibuka
    LaunchedEffect(Unit) {
        viewModel.fetchAllBids()
    }

    Column(modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)) {
        Spacer(Modifier.height(16.dp))
        Text(
            "All Auction Bids",
            style = MaterialTheme.typography.headlineSmall,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
        Text(
            "Total ${allBids.size} Active Bids",
            color = Color.Gray
        )

        Spacer(Modifier.height(20.dp))

        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally), color = Color(0xFFFFD700))
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 20.dp)
            ) {
                // Filter berdasarkan search dan urutkan dari bidPrice tertinggi
                val filtered = allBids.filter {
                    it.flashSale?.name?.contains(search, true) == true ||
                            it.userId.toString().contains(search)
                }.sortedByDescending { it.bidPrice.toLongOrNull() ?: 0L }

                itemsIndexed(filtered) { index, bid ->
                    BidUserCard(
                        bid = bid,
                        isTopBid = index == 0,
                        onPickWinner = { selectedWinner = bid }
                    )
                }
            }
        }
    }

    // Dialog Konfirmasi Pemenang
    if (selectedWinner != null) {
        AlertDialog(
            onDismissRequest = { selectedWinner = null },
            containerColor = Color(0xFF1A1A1A),
            title = { Text("Confirm Winner", color = Color.White) },
            text = {
                Text(
                    "Pick User #${selectedWinner?.userId} as the winner for ${selectedWinner?.flashSale?.name} with bid ${formatRupiah(selectedWinner?.bidPrice?.toLongOrNull() ?: 0L)}?",
                    color = Color.Gray
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.selectWinner(selectedWinner!!.id) {
                            selectedWinner = null
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00FF85))
                ) {
                    Text("Confirm", color = Color.Black, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { selectedWinner = null }) {
                    Text("Cancel", color = Color.White)
                }
            }
        )
    }
}