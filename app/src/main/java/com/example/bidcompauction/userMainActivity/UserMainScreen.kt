package com.example.bidcompauction.userMainActivity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bidcompauction.R
import com.example.bidcompauction.userMainActivity.components.*


import androidx.lifecycle.viewmodel.compose.viewModel
import data.model.AdminProductResponse
import data.viewmodel.ProductViewModel

import com.example.bidcompauction.userMainActivity.checkout.CartItemUi // Pastikan import ini ada
import data.model.AdminFlashSaleResponse
import data.viewmodel.BidsViewModel
import data.viewmodel.FlashSaleViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserMainScreen(
    onOpenNotifications: () -> Unit,
    onOpenProfile: () -> Unit,
    onLogout: () -> Unit,
    onCheckout: (List<CartItemUi>) -> Unit,
    onInvoice: () -> Unit,
    // FIX: Sesuaikan parameter agar konsisten dengan pemanggilan di bawah
    onOpenMyBids: () -> Unit,
    productViewModel: ProductViewModel = viewModel(),
    flashSaleViewModel: FlashSaleViewModel = viewModel(),
    bidsViewModel: BidsViewModel = viewModel()
) {
    // --- State Management ---
    val products by productViewModel.products.collectAsState()
    var cartItems by remember { mutableStateOf(listOf<CartItemUi>()) }

    val flashSales by flashSaleViewModel.items.collectAsState()
    val isLoadingProducts by productViewModel.isLoading.collectAsState()
    val isLoadingFlash by flashSaleViewModel.isLoading.collectAsState()

    // State Loading dari Bids API
    val isBidding by bidsViewModel.isLoading.collectAsState()

    val notifCount = remember { mutableIntStateOf(3) }
    var showLogoutDialog by remember { mutableStateOf(false) }
    var search by remember { mutableStateOf("") }
    var selectedTab by remember { mutableStateOf(MainTab.Home) }

    // Fetch data saat layar dibuka
    LaunchedEffect(Unit) {
        productViewModel.fetchProducts()
        flashSaleViewModel.fetchFlashSales()
    }

    // Filter produk
    val filteredProducts = remember(search, products) {
        val q = search.trim().lowercase()
        if (q.isEmpty()) products else products.filter { it.name.lowercase().contains(q) }
    }

    // State untuk Bid
    var selectedBidItem by remember { mutableStateOf<AdminFlashSaleResponse?>(null) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    Scaffold(
        topBar = {
            PremiumTopBar(
                search = search,
                onSearchChange = { search = it },
                notifCount = notifCount.intValue,
                onOpenNotifications = onOpenNotifications,
                onOpenProfile = onOpenProfile,
                onLogout = { showLogoutDialog = true }
            )
        },
        bottomBar = {
            PremiumBottomBar(
                selected = selectedTab,
                onSelect = { tab ->
                    selectedTab = tab
                    when (tab) {
                        MainTab.Checkout -> onCheckout(cartItems)
                        MainTab.MyBids -> onOpenMyBids() // Memanggil tanpa parameter
                        MainTab.Invoice -> onInvoice()
                        else -> Unit
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Brush.verticalGradient(listOf(Color(0xFF0B0B0B), Color(0xFF161616), Color(0xFF0B0B0B))))
        ) {
            if (isLoadingProducts && products.isEmpty()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center), color = Color(0xFFFFD700))
            } else {
                HomeContent(
                    flashSales = flashSales,
                    products = filteredProducts,
                    onFlashSaleClick = { selectedBidItem = it },
                    onAddToCart = { product ->
                        val newItem = CartItemUi(
                            id = product.id.toString(),
                            name = product.name,
                            price = (product.price.toString().toDoubleOrNull() ?: 0.0).toLong(),
                            imageUrl = product.images.firstOrNull() ?: "",
                            qty = 1
                        )
                        val existingIndex = cartItems.indexOfFirst { it.id == newItem.id }
                        if (existingIndex != -1) {
                            cartItems = cartItems.toMutableList().apply {
                                this[existingIndex] = this[existingIndex].copy(qty = this[existingIndex].qty + 1)
                            }
                        } else {
                            cartItems = cartItems + newItem
                        }
                    }
                )
            }

            // Loading Overlay saat proses POST Bid ke server
            if (isBidding) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.6f))
                        .clickable(enabled = false) {} // Mencegah klik tembus ke bawah
                ) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center), color = Color(0xFFFFD700))
                }
            }
        }

        // --- Bid Bottom Sheet (Hanya Satu yang Menggunakan API) ---
        if (selectedBidItem != null) {
            ModalBottomSheet(
                onDismissRequest = { selectedBidItem = null },
                sheetState = sheetState,
                containerColor = Color(0xFF1C1C1C),
                scrimColor = Color.Black.copy(alpha = 0.7f),
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
            ) {
                BidBottomSheetContent(
                    item = selectedBidItem!!,
                    onConfirmBid = { bidValue ->
                        // Gunakan BidsViewModel (Integrasi API)
                        selectedBidItem?.let { item ->
                            bidsViewModel.placeBid(
                                flashSaleId = item.id,
                                bidPrice = bidValue,
                                onSuccess = {
                                    selectedBidItem = null
                                    selectedTab = MainTab.MyBids
                                    onOpenMyBids()
                                }
                            )
                        }
                    }
                )
            }
        }
    }

    // Logout Dialog
    LogoutConfirmDialog(
        open = showLogoutDialog,
        onDismiss = { showLogoutDialog = false },
        onConfirmLogout = onLogout
    )
}

/* ---------------- UI COMPONENTS ---------------- */

@Composable
private fun HomeContent(
    flashSales: List<AdminFlashSaleResponse>,
    products: List<AdminProductResponse>,
    // FIX: Ubah parameter agar menerima AdminFlashSaleResponse, bukan FlashSaleUi
    onFlashSaleClick: (AdminFlashSaleResponse) -> Unit,
    onAddToCart: (AdminProductResponse) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Spacer(Modifier.height(12.dp))

        SectionHeader(title = "Flash Sale", subtitle = "Live Auction")

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(end = 8.dp)
        ) {
            // Menggunakan key agar recomposition lebih efisien
            items(flashSales, key = { it.id }) { item ->
                FlashSaleCard(
                    item = item,
                    // Sekarang tipe data sudah sinkron
                    onBidClick = { onFlashSaleClick(item) }
                )
            }
        }

        Spacer(Modifier.height(20.dp))

        SectionHeader(title = "Recommended", subtitle = "${products.size} items")

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(bottom = 80.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(products, key = { it.id }) { p ->
                ProductCard(
                    p = p,
                    onAdd = { onAddToCart(p) }
                )
            }
        }
    }
}