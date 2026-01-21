package com.example.bidcompauction.userMainActivity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.ui.graphics.Color

class UserMainActivity : ComponentActivity() {
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
                Surface {
                    UserMainNavGraph(
                        onLogout = {
                            // TODO clear session
                            finish() // balik ke LoginActivity
                        }
                    )
                }
            }
        }
    }
}