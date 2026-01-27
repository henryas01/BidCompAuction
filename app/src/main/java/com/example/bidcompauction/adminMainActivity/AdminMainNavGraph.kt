package com.example.bidcompauction.adminMainActivity

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.bidcompauction.adminMainActivity.bids.BidsScreen
import com.example.bidcompauction.adminMainActivity.flashsale.FlashSaleScreen
import com.example.bidcompauction.adminMainActivity.invoices.InvoicesScreen
import com.example.bidcompauction.adminMainActivity.products.ProductsScreen

@Composable
fun AdminMainNavGraph(
    navController: NavHostController,
    search: String
) {
    NavHost(
        navController = navController,
        startDestination = AdminRoute.PRODUCTS
    ) {
        composable(AdminRoute.PRODUCTS) {
            ProductsScreen(
                search = search
            )
        }
        composable(AdminRoute.FLASHSALE) {
            FlashSaleScreen(
                search = search
            )
        }
        composable(AdminRoute.BIDS) {
            BidsScreen(
                search = search
            )
        }
        composable(AdminRoute.INVOICES) {
            InvoicesScreen(
                search = search
            )
        }
    }
}
