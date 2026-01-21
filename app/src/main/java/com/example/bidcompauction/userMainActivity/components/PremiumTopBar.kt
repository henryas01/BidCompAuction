package com.example.bidcompauction.userMainActivity.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Logout
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PremiumTopBar(
    search: String,
    onSearchChange: (String) -> Unit,
    notifCount: Int,
    onOpenNotifications: () -> Unit,
    onOpenProfile: () -> Unit,
    onLogout: () -> Unit
) {
    Column(
        modifier = Modifier
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFF0B0B0B), Color(0xFF121212))
                )
            )
            .padding(bottom = 10.dp)
    ) {
        TopAppBar(
            title = {
                Column {
                    Text("BID CompAuction", fontWeight = FontWeight.ExtraBold)
                    Text(
                        "Premium hardware marketplace",
                        style = MaterialTheme.typography.labelMedium,
                        color = Color.White.copy(alpha = 0.7f)
                    )
                }
            },
            actions = {
                Box {
                    IconButton(onClick = onOpenNotifications) {
                        Icon(
                            imageVector = Icons.Filled.Notifications,
                            contentDescription = "Notifications",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    if (notifCount > 0) {
                        Badge(
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .offset(x = (-2).dp, y = 4.dp),
                            containerColor = Color(0xFFFF3D00)
                        ) {
                            Text(if (notifCount > 9) "9+" else notifCount.toString())
                        }
                    }
                }

                // ⚙️ Settings
                var expanded by remember { mutableStateOf(false) }

                Box {
                    IconButton(onClick = { expanded = true }) {
                        Icon(
                            imageVector = Icons.Filled.Settings,
                            contentDescription = "Settings",
                            tint = Color.White
                        )
                    }

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier.background(Color(0xFF1A1A1A))
                    ) {
                        DropdownMenuItem(
                            text = { Text("Profile") },
                            leadingIcon = {
                                Icon(Icons.Filled.Person, contentDescription = "Profile")
                            },
                            onClick = {
                                expanded = false
                                onOpenProfile()
                            }
                        )

                        DropdownMenuItem(
                            text = { Text("Logout", color = Color(0xFFFF6B6B)) },
                            leadingIcon = {
                                Icon(
                                    Icons.Filled.Logout,
                                    contentDescription = "Logout",
                                    tint = Color(0xFFFF6B6B)
                                )
                            },
                            onClick = {
                                expanded = false
                                onLogout()
                            }
                        )
                    }
                }
            }
        )

        TextField(
            value = search,
            onValueChange = onSearchChange,
            singleLine = true,
            leadingIcon = {
                Icon(Icons.Filled.Search, contentDescription = "Search", tint = Color(0xFF3A3A3A))
            },
            placeholder = { Text("Search barang...", color = Color(0xFF8A8A8A)) },
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
