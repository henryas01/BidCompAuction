package com.example.bidcompauction.userMainActivity


import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bidcompauction.userMainActivity.checkout.CheckoutScreen
import com.example.bidcompauction.userMainActivity.invoice.InvoiceScreen
import com.example.bidcompauction.userMainActivity.notification.NotificationScreen
import com.example.bidcompauction.userMainActivity.profile.ProfileScreen

object MainRoute {
    const val HOME = "home"
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

    NavHost(
        navController = navController,
        startDestination = MainRoute.HOME
    ) {
        composable(MainRoute.HOME) {
            UserMainScreen(
                onOpenNotifications = {  navController.navigate(MainRoute.NOTIFICATION) },
                onOpenProfile = {
                    navController.navigate(MainRoute.PROFILE)
                },
                onLogout = {
                    onLogout()
                },
                onCheckout = {
                    navController.navigate(MainRoute.CHECKOUT)
                },
                onInvoice = {
                    navController.navigate(MainRoute.INVOICE)
                }
            )
        }

        composable(MainRoute.CHECKOUT) {
            CheckoutScreen(
                onBack = {
                    navController.popBackStack()
                },
                onPay = { total ->
                    // TODO proses payment
                    navController.popBackStack()
                }
            )
        }

        composable(MainRoute.INVOICE) {
            InvoiceScreen(
                onBack = { navController.popBackStack() },
                onInvoiceClick = { invoice ->
                    // TODO navigate to invoice detail
                }
            )
        }

        composable(MainRoute.PROFILE) {
            ProfileScreen(
                onBack = { navController.popBackStack() },
                onEditProfile = {
                    // TODO navigate to EditProfileScreen
                },
                onLogout = {
                    onLogout()
                }
            )
        }

        composable(MainRoute.NOTIFICATION) {
            NotificationScreen(
                onBack = { navController.popBackStack() },
                onOpenNotification = { notif ->
                    // TODO: bisa navigate ke detail invoice/order/promo
                }
            )
        }

    }
}
