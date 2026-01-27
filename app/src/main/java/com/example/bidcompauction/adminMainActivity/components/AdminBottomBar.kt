package com.example.bidcompauction.adminMainActivity.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ListAlt
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.example.bidcompauction.adminMainActivity.AdminRoute

@Composable
fun AdminBottomBar(
    currentRoute: String,
    onNavigate: (String) -> Unit
) {
    NavigationBar(containerColor = Color(0xFF0F0F0F)) {
        NavigationBarItem(
            selected = currentRoute == AdminRoute.PRODUCTS,
            onClick = { onNavigate(AdminRoute.PRODUCTS) },
            icon = { Icon(Icons.Filled.Inventory2, contentDescription = "Products") },
            label = { Text("Products") }
        )
        NavigationBarItem(
            selected = currentRoute == AdminRoute.FLASHSALE,
            onClick = { onNavigate(AdminRoute.FLASHSALE) },
            icon = { Icon(Icons.Filled.Bolt, contentDescription = "Flash") },
            label = { Text("Flash") }
        )
//        NavigationBarItem(
//            selected = currentRoute == AdminRoute.WINNERS,
//            onClick = { onNavigate(AdminRoute.WINNERS) },
//            icon = { Icon(Icons.Filled.EmojiEvents, contentDescription = "Winners") },
//            label = { Text("Winners") }
//        )


        NavigationBarItem(
            selected = currentRoute == AdminRoute.BIDS,
            onClick = { onNavigate(AdminRoute.BIDS) },
            icon = {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ListAlt,
                    contentDescription = "List Bids"
                )
            },
            label = {
                Text(
                    text = "List Bids",
                    fontWeight = if (currentRoute == AdminRoute.BIDS) FontWeight.Bold else FontWeight.Normal
                )
            }
        )
        NavigationBarItem(
            selected = currentRoute == AdminRoute.INVOICES,
            onClick = { onNavigate(AdminRoute.INVOICES) },
            icon = { Icon(Icons.Filled.ReceiptLong, contentDescription = "Invoices") },
            label = { Text("Invoices") }
        )
    }
}
