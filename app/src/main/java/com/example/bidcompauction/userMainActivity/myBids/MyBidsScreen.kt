package com.example.bidcompauction.userMainActivity.myBids

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bidcompauction.userMainActivity.myBids.components.BidItemCard
import com.example.bidcompauction.userMainActivity.myBids.components.EmptyBidsState
import com.example.bidcompauction.userMainActivity.components.PaymentSheetContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bidcompauction.data.model.BidsResponse
import data.viewmodel.BidsViewModel
import androidx.compose.ui.Alignment


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyBidsScreen(
    onBack: () -> Unit,
    onBidAgain: (String) -> Unit,
    bidsViewModel: BidsViewModel = viewModel()
) {
    val bidList by bidsViewModel.myBids.collectAsState()
    val isLoading by bidsViewModel.isLoading.collectAsState()

    var selectedWinningBid by remember { mutableStateOf<BidsResponse?>(null) }
    val paymentSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    // Trigger awal
    LaunchedEffect(Unit) {
        bidsViewModel.fetchMyBids()
    }

    Scaffold(
        containerColor = Color(0xFF0B0B0B),
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("My Active Bids", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color(0xFF0F0F0F))
            )
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding).background(Color(0xFF0B0B0B))) {
            if (isLoading && bidList.isEmpty()) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = Color(0xFFFFD700)
                )
            } else if (bidList.isEmpty()) {
                EmptyBidsState()
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(bidList, key = { it.id }) { bid ->
                        BidItemCard(
                            bidResponse = bid,
                            onBidAgain = { onBidAgain(bid.flashSaleId.toString()) },
                            onPayNow = {
                                // Hanya set jika status memungkinkan untuk bayar
                                selectedWinningBid = bid
                            }
                        )
                    }
                }
            }
        }

        if (selectedWinningBid != null) {
            ModalBottomSheet(
                onDismissRequest = { selectedWinningBid = null },
                sheetState = paymentSheetState,
                containerColor = Color(0xFF1A1A1A),
                scrimColor = Color.Black.copy(alpha = 0.8f)
            ) {
                PaymentSheetContent(
                    sourceId = selectedWinningBid!!.id,
                    sourceType = "BID",
                    title = selectedWinningBid!!.flashSale?.name ?: "Auction Item",
                    price = selectedWinningBid!!.bidPrice.toLongOrNull() ?: 0L,
                    onDismiss = {
                        selectedWinningBid = null
                        // Refresh data agar status berubah setelah upload bukti
                        bidsViewModel.fetchMyBids()
                    }
                )
            }
        }
    }
}