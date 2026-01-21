package com.example.bidcompauction.userMainActivity.components


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.bidcompauction.userMainActivity.MainTab

@Composable
fun PremiumBottomBar(
    selected: MainTab,
    onSelect: (MainTab) -> Unit
) {
    NavigationBar(containerColor = Color(0xFF0F0F0F)) {
        NavigationBarItem(
            selected = selected == MainTab.Home,
            onClick = { onSelect(MainTab.Home) },
            icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
            label = { Text("Home") }
        )
        NavigationBarItem(
            selected = selected == MainTab.Checkout,
            onClick = { onSelect(MainTab.Checkout) },
            icon = { Icon(Icons.Filled.ShoppingCart, contentDescription = "Checkout") },
            label = { Text("Checkout") }
        )
        NavigationBarItem(
            selected = selected == MainTab.Invoice,
            onClick = { onSelect(MainTab.Invoice) },
            icon = { Icon(Icons.Filled.ReceiptLong, contentDescription = "Invoice") },
            label = { Text("Invoice") }
        )
    }
}
