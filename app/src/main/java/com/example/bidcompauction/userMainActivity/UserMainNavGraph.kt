package com.example.bidcompauction.userMainActivity

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bidcompauction.userMainActivity.checkout.CheckoutScreen
import com.example.bidcompauction.userMainActivity.invoice.InvoiceScreen
import com.example.bidcompauction.userMainActivity.myBids.MyBidsScreen
import com.example.bidcompauction.userMainActivity.notification.NotificationScreen
import com.example.bidcompauction.userMainActivity.profile.ProfileScreen
import data.model.AdminFlashSaleResponse
import data.viewmodel.CartViewModel

object MainRoute {
    const val HOME = "home"
    const val MY_BIDS = "my_bids" // Rute baru untuk daftar bid
    const val CHECKOUT = "checkout"
    const val INVOICE = "invoice"
    const val PROFILE = "profile"
    const val NOTIFICATION = "notification"
}

@Composable
fun UserMainNavGraph(
    onLogout: () -> Unit = {}
) {
    val navController = rememberNavController()
    val cartViewModel: CartViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = MainRoute.HOME
    ) {
        composable(MainRoute.HOME) {
            UserMainScreen(
                onOpenNotifications = { navController.navigate(MainRoute.NOTIFICATION) },
                onOpenProfile = { navController.navigate(MainRoute.PROFILE) },
                onLogout = { onLogout() },
                onCheckout = { itemsFromHome ->
                    itemsFromHome.forEach { cartViewModel.addToCart(it) }
                    navController.navigate(MainRoute.CHECKOUT)
                },
                onInvoice = { navController.navigate(MainRoute.INVOICE) },
                // FIX: Terima list activeBids dari UserMainScreen dan simpan ke SavedStateHandle
                onOpenMyBids = {
                    // Cukup navigasi biasa, tidak perlu bawa data list lagi
                    navController.navigate(MainRoute.HOME) {
                        // Opsional: Pastikan tidak menumpuk backstack
                        launchSingleTop = true
                    }
                    navController.navigate(MainRoute.MY_BIDS)
                }
            )
        }

        composable(MainRoute.CHECKOUT) {
            CheckoutScreen(
                cartItems = cartViewModel.cartItems,
                onBack = { navController.popBackStack() },
                onUpdateQty = { id, qty -> cartViewModel.updateQty(id, qty) },
                onRemoveItem = { id -> cartViewModel.removeItem(id) }
            )
        }

        composable(MainRoute.MY_BIDS) {
            // FIX: Ambil data bidList dari SavedStateHandle
            val bidList = navController.previousBackStackEntry
                ?.savedStateHandle
                ?.get<List<AdminFlashSaleResponse>>("bids_data") ?: emptyList()

            MyBidsScreen(
                onBack = { navController.popBackStack() },
                onBidAgain = { navController.popBackStack(MainRoute.HOME, false) }
            )
        }

        composable(MainRoute.INVOICE) {
            InvoiceScreen(onBack = { navController.popBackStack() }, onInvoiceClick = {})
        }

        composable(MainRoute.PROFILE) {
            ProfileScreen(onBack = { navController.popBackStack() }, onEditProfile = {}, onLogout = { onLogout() })
        }

        composable(MainRoute.NOTIFICATION) {
            NotificationScreen(onBack = { navController.popBackStack() }, onOpenNotification = {})
        }
    }
}