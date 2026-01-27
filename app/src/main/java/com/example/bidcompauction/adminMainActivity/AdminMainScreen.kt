package com.example.bidcompauction.adminMainActivity

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.bidcompauction.adminMainActivity.components.AdminBottomBar
import com.example.bidcompauction.adminMainActivity.components.AdminTopBar

@Composable
fun AdminMainScreen(
    onLogout: () -> Unit
) {
    val navController = rememberNavController()
    val backStack by navController.currentBackStackEntryAsState()
    val currentRoute = backStack?.destination?.route ?: AdminRoute.PRODUCTS

    var search by remember { mutableStateOf("") }

    val bg = Brush.verticalGradient(
        listOf(Color(0xFF0B0B0B), Color(0xFF0F0F0F), Color(0xFF0B0B0B))
    )

    Scaffold(
        topBar = {
            AdminTopBar(
                currentRoute = currentRoute,
                search = search,
                onSearchChange = { search = it },
                onLogout = onLogout
            )
        },
        bottomBar = {
            AdminBottomBar(
                currentRoute = currentRoute,
                onNavigate = { route ->
                    navController.navigate(route) {
                        launchSingleTop = true
                        restoreState = true
                        popUpTo(AdminRoute.PRODUCTS) { saveState = true }
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(bg)
        ) {
            AdminMainNavGraph(
                navController = navController,
                search = search
            )
        }
    }
}
