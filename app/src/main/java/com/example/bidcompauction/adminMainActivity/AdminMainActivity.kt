package com.example.bidcompauction.adminMainActivity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.compose.ui.graphics.Color
import com.example.bidcompauction.utils.AuthManager
import com.example.bidcompauction.utils.NavigationUtils

class AdminMainActivity : ComponentActivity() {
    private val authManager by lazy { AuthManager(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme(
                colorScheme = darkColorScheme(
                    primary = Color(0xFFFFC107),
                    onPrimary = Color(0xFF111111),
                    background = Color(0xFF0B0B0B),
                    surface = Color(0xFF121212),
                    onBackground = Color.White,
                    onSurface = Color.White
                )
            ) {
                Surface(color = MaterialTheme.colorScheme.background) {
                    AdminMainScreen(
                        onLogout = {
                            NavigationUtils.logoutAndRedirect(authManager, this@AdminMainActivity)
                        }
                    )
                }
            }
        }
    }
}

