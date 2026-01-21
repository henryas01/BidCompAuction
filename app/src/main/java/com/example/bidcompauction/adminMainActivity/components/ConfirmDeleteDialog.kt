package com.example.bidcompauction.adminMainActivity.components


import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight



@Composable
fun ConfirmDeleteDialog(
    open: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    if (!open) return

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Delete item?", fontWeight = FontWeight.Bold) },
        text = { Text("This action cannot be undone.") },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("Delete", color = Color(0xFFFF6B6B))
            }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancel") } },
        containerColor = Color(0xFF141414)
    )
}
