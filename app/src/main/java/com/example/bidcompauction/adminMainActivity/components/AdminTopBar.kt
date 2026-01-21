package com.example.bidcompauction.adminMainActivity.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.bidcompauction.adminMainActivity.AdminRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminTopBar(
    currentRoute: String,
    search: String,
    onSearchChange: (String) -> Unit,
    onAdd: () -> Unit,
    onLogout: () -> Unit
) {
    Column(
        modifier = Modifier
            .background(Color(0xFF0B0B0B))
            .padding(bottom = 10.dp)
    ) {
        TopAppBar(
            title = {
                Column {
                    Text("Admin Panel", fontWeight = FontWeight.ExtraBold)
                    Text(
                        when (currentRoute) {
                            AdminRoute.PRODUCTS -> "Manage Products"
                            AdminRoute.FLASHSALE -> "Manage Flash Sale"
                            AdminRoute.WINNERS -> "Pick Bid Winners"
                            AdminRoute.INVOICES -> "User Invoices"
                            else -> "Dashboard"
                        },
                        style = MaterialTheme.typography.labelMedium,
                        color = Color.White.copy(alpha = 0.7f)
                    )
                }
            },
            actions = {
                val showAdd = currentRoute == AdminRoute.PRODUCTS || currentRoute == AdminRoute.FLASHSALE
                if (showAdd) {
                    IconButton(onClick = onAdd) {
                        Icon(Icons.Filled.Add, contentDescription = "Add", tint = MaterialTheme.colorScheme.primary)
                    }
                }
                IconButton(onClick = { /* TODO admin settings */ }) {
                    Icon(Icons.Filled.Settings, contentDescription = "Settings", tint = Color.White)
                }
                IconButton(onClick = onLogout) {
                    Icon(Icons.Filled.Logout, contentDescription = "Logout", tint = Color(0xFFFF6B6B))
                }
            }
        )

        TextField(
            value = search,
            onValueChange = onSearchChange,
            singleLine = true,
            leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Search", tint = Color(0xFF3A3A3A)) },
            placeholder = { Text("Search products, invoices, users...", color = Color(0xFF8A8A8A)) },
            shape = RoundedCornerShape(18.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedTextColor = Color(0xFF111111),
                unfocusedTextColor = Color(0xFF111111),
                cursorColor = MaterialTheme.colorScheme.primary,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .height(54.dp)
        )
    }
}
