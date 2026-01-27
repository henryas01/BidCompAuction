package com.example.bidcompauction.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.example.bidcompauction.adminMainActivity.AdminMainActivity
import com.example.bidcompauction.userMainActivity.UserMainActivity
import com.example.bidcompauction.LoginActivity

object NavigationUtils {

    fun navigateToDashboard(context: Context, authManager: AuthManager) {
        val user = authManager.getUserData()
        val intent = if (user?.isAdmin == true) {
            Intent(context, AdminMainActivity::class.java)
        } else {
            Intent(context, UserMainActivity::class.java)
        }

        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        context.startActivity(intent)
        (context as? Activity)?.finish()
    }

    fun logoutAndRedirect(authManager: AuthManager, context: Context) {
        authManager.logout()
        val intent = Intent(context, LoginActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
        context.startActivity(intent)
        (context as? Activity)?.finish()
    }
}