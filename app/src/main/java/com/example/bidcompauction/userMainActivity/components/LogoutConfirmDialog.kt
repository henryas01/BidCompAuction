package com.example.bidcompauction.userMainActivity.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight

@Composable
fun LogoutConfirmDialog(
    open: Boolean,
    onDismiss: () -> Unit,
    onConfirmLogout: () -> Unit
) {
    if (!open) return

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Logout?",
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Text("Kamu yakin mau keluar dari akun ini?")
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onDismiss()
                    onConfirmLogout()
                }
            ) {
                Text(
                    text = "Logout",
                    color = Color(0xFFFF6B6B),
                    fontWeight = FontWeight.Bold
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        },
        containerColor = Color(0xFF141414),
        titleContentColor = Color.White,
        textContentColor = Color.White.copy(alpha = 0.8f)
    )
}
