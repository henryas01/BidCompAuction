package com.example.bidcompauction.userMainActivity.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.bidcompauction.userMainActivity.profile.components.ProfileMenuItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    onBack: () -> Unit,
    onEditProfile: () -> Unit,
    onLogout: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profile") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF0B0B0B))
                .padding(padding)
                .padding(16.dp)
        ) {

            // Avatar + Info
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Surface(
                    shape = CircleShape,
                    color = Color(0x22FFFFFF),
                    modifier = Modifier.size(90.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Person,
                        contentDescription = "Avatar",
                        tint = Color.White,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(20.dp)
                    )
                }

                Spacer(Modifier.height(12.dp))

                Text(
                    text = "John Doe",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "john.doe@email.com",
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.White.copy(alpha = 0.7f)
                )
            }

            Spacer(Modifier.height(24.dp))

            Divider(color = Color.White.copy(alpha = 0.15f))

            // Menu list
            ProfileMenuItem(
                icon = Icons.Filled.Edit,
                title = "Edit Profile",
                subtitle = "Change your personal information",
                onClick = onEditProfile
            )

            ProfileMenuItem(
                icon = Icons.Filled.Phone,
                title = "Phone Number",
                subtitle = "+62 812-xxxx-xxxx",
                onClick = {}
            )

            ProfileMenuItem(
                icon = Icons.Filled.LocationOn,
                title = "Address",
                subtitle = "Set your shipping address",
                onClick = {}
            )

            Divider(color = Color.White.copy(alpha = 0.15f))

            ProfileMenuItem(
                icon = Icons.Filled.Logout,
                title = "Logout",
                danger = true,
                onClick = onLogout
            )
        }
    }
}
