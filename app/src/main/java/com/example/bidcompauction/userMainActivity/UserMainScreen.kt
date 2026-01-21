package com.example.bidcompauction.userMainActivity

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.bidcompauction.R
import com.example.bidcompauction.userMainActivity.components.*

@Composable
fun UserMainScreen(
    onOpenNotifications: () -> Unit,
    onOpenProfile: () -> Unit,
    onLogout: () -> Unit,
    onCheckout: () -> Unit,
    onInvoice: () -> Unit
) {
    val notifCount = remember { mutableIntStateOf(3) }

    val now = remember { System.currentTimeMillis() }

    var showLogoutDialog by remember { mutableStateOf(false) }


    val products = remember {
        listOf(
            ProductUi("p1", "RTX 3060 Ti", 4_200_000, 8, R.drawable.gpu),
            ProductUi("p2", "SSD NVMe 1TB", 950_000, 24, R.drawable.gpu),
            ProductUi("p3", "RAM DDR4 16GB", 550_000, 40, R.drawable.gpu),
            ProductUi("p4", "PSU 650W Gold", 1_200_000, 12, R.drawable.gpu),
            ProductUi("p5", "Monitor 144Hz", 2_100_000, 7, R.drawable.gpu),
            ProductUi("p6", "Cooling AIO 240", 1_350_000, 9, R.drawable.gpu),
        )
    }

    val flashSales = remember {
        listOf(
            FlashSaleUi("f1", "RTX 4070 SUPER", 11_500_000, 9_999_000, 3, now + 45 * 60_000L, R.drawable.gpu),
            FlashSaleUi("f2", "SSD NVMe 2TB", 1_750_000, 1_399_000, 10, now + 20 * 60_000L, R.drawable.gpu),
            FlashSaleUi("f3", "Mechanical Keyboard", 650_000, 499_000, 15, now + 75 * 60_000L, R.drawable.gpu),
        )
    }

    var search by remember { mutableStateOf("") }
    var selectedTab by remember { mutableStateOf(MainTab.Home) }

    val filteredProducts = remember(search, products) {
        val q = search.trim().lowercase()
        if (q.isEmpty()) products else products.filter {
            it.name.lowercase().contains(q)
        }
    }

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
                        MainTab.Home -> Unit
                        MainTab.Checkout -> onCheckout()
                        MainTab.Invoice -> onInvoice()
                    }
                }
            )
        }
    ) { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color(0xFF0B0B0B),
                            Color(0xFF0F0F0F),
                            Color(0xFF0B0B0B)
                        )
                    )
                )
        ) {
            HomeContent(
                flashSales = flashSales,
                products = filteredProducts
            )
        }
    }
    LogoutConfirmDialog(
        open = showLogoutDialog,
        onDismiss = { showLogoutDialog = false },
        onConfirmLogout = onLogout
    )
}

/* ---------------- HOME CONTENT ---------------- */

@Composable
private fun HomeContent(
    flashSales: List<FlashSaleUi>,
    products: List<ProductUi>
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Spacer(Modifier.height(12.dp))

        SectionHeader(
            title = "Flash Sale",
            subtitle = "Limited time deals"
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(end = 6.dp)
        ) {
            items(flashSales, key = { it.id }) { item ->
                FlashSaleCard(item = item)
            }
        }

        Spacer(Modifier.height(18.dp))

        SectionHeader(
            title = "Recommended",
            subtitle = "${products.size} items"
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(bottom = 16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(products, key = { it.id }) { p ->
                ProductCard(p = p)
            }
        }
    }
}
