package com.example.bidcompauction

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bidcompauction.ui.theme.BidTheme
import com.example.bidcompauction.utils.AuthManager
import com.example.bidcompauction.utils.NavigationUtils

class OpeningActivity : ComponentActivity() {

    private val authManager by lazy { AuthManager(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // checks if have been login
        if (authManager.isLoggedIn()) {
            NavigationUtils.navigateToDashboard(this, authManager)
            return
        }

        setContent {
            BidTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    OpeningScreen(
                        onLoginClick = {
                             startActivity(Intent(this, LoginActivity::class.java))
                        }
                    )
                }
            }
        }
    }
}
@Composable
fun OpeningScreen(
    onLoginClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF080808)) // Hitam solid agar aksen glow lebih terlihat
    ) {
        Box(
            modifier = Modifier
                .size(350.dp)
                .offset(x = (-100).dp, y = (-50).dp)
                .background(
                    Brush.radialGradient(
                        colors = listOf(Color(0x12FFC107), Color.Transparent)
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 28.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Spacer(Modifier.height(80.dp))

            // --- HEADER SECTION ---
            Column {
                Text(
                    text = "BID",
                    style = MaterialTheme.typography.displayMedium.copy(
                        fontWeight = FontWeight.Black,
                        letterSpacing = 4.sp
                    ),
                    color = Color.White
                )
                Text(
                    text = "CompAuction",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.ExtraLight,
                        letterSpacing = 1.sp
                    ),
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(Modifier.height(16.dp))

            // Subtitle dengan line height yang lebih nyaman
            Text(
                text = "Auction komputer & part terbaik.\nCepat, aman, dan transparan.",
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = Color.White.copy(alpha = 0.5f),
                    lineHeight = 24.sp,
                    fontWeight = FontWeight.Light
                )
            )

            Spacer(Modifier.weight(1f))

            // --- BUTTON SECTION ---
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = onLoginClick,
                    shape = RoundedCornerShape(16.dp), // Konsisten dengan LoginScreen yang baru
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = Color.Black
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(58.dp)
                        .shadow(
                            elevation = 12.dp,
                            shape = RoundedCornerShape(16.dp),
                            clip = false,
                            ambientColor = MaterialTheme.colorScheme.primary,
                            spotColor = MaterialTheme.colorScheme.primary
                        )
                ) {
                    Text(
                        text = "GET STARTED",
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 2.sp
                        )
                    )
                }

                Spacer(Modifier.height(20.dp))

                Text(
                    text = "Ikut lelang & pantau bid secara real-time",
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.White.copy(alpha = 0.3f)
                )
            }

            Spacer(Modifier.height(40.dp))

            // --- FEATURED PRODUCT CARD (GPU) ---
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .clip(RoundedCornerShape(28.dp))
                    .background(Color(0x0AFFFFFF)) // Glassmorphism
                    .border(1.dp, Color(0x12FFFFFF), RoundedCornerShape(28.dp))
            ) {
                // Background Glow kecil di dalam card
                Box(
                    modifier = Modifier
                        .size(150.dp)
                        .align(Alignment.Center)
                        .background(
                            Brush.radialGradient(
                                colors = listOf(Color(0x08FFFFFF), Color.Transparent)
                            )
                        )
                )

                Image(
                    painter = painterResource(id = R.drawable.gpu),
                    contentDescription = "Featured Product",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp)
                )

                // Fading Overlay (Gradient)
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                listOf(Color.Transparent, Color(0x44000000))
                            )
                        )
                )

                // Featured Badge dengan gaya yang lebih clean
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color.White.copy(alpha = 0.1f),
                    border = BorderStroke(1.dp, Color.White.copy(alpha = 0.1f)),
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(16.dp)
                ) {
                    Text(
                        text = "NEW ARRIVAL",
                        color = Color.White,
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        ),
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                    )
                }
            }
            Spacer(Modifier.height(24.dp))
        }
    }
}

@Preview(
    name = "Small Phone",
    device = Devices.PIXEL_3A,
    showBackground = true
)
@Composable
fun OpeningScreenMultiPreview() {
    BidTheme {
        OpeningScreen(onLoginClick = {})
    }
}


