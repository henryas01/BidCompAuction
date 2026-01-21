package com.example.bidcompauction.adminMainActivity.flashsale.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.bidcompauction.R
import com.example.bidcompauction.adminMainActivity.flashsale.AdminFlashSale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminFlashSaleDialog(
    open: Boolean,
    initial: AdminFlashSale?,
    onDismiss: () -> Unit,
    onSave: (AdminFlashSale) -> Unit
) {
    if (!open) return

    var name by remember(open) { mutableStateOf(initial?.name ?: "") }
    var minutes by remember(open) { mutableStateOf("30") }
    var price by remember(open) { mutableStateOf(initial?.price?.toString() ?: "") }
    var qty by remember(open) { mutableStateOf(initial?.qty?.toString() ?: "") }
    var desc by remember(open) { mutableStateOf(initial?.description ?: "") }

    val canSave =
        name.isNotBlank() &&
                minutes.toLongOrNull() != null &&
                price.toLongOrNull() != null &&
                qty.toIntOrNull() != null

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = if (initial == null) "Create Flash Sale" else "Edit Flash Sale",
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Product Name") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = minutes,
                    onValueChange = { minutes = it.filter(Char::isDigit) },
                    label = { Text("Countdown (minutes)") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = price,
                    onValueChange = { price = it.filter(Char::isDigit) },
                    label = { Text("Price") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = qty,
                    onValueChange = { qty = it.filter(Char::isDigit) },
                    label = { Text("Quantity") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = desc,
                    onValueChange = { desc = it },
                    label = { Text("Description") },
                    minLines = 2,
                    modifier = Modifier.fillMaxWidth()
                )

                Text(
                    text = "Image: using placeholder (image picker coming next)",
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.White.copy(alpha = 0.6f)
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val endTime =
                        System.currentTimeMillis() + (minutes.toLong() * 60_000L)

                    onSave(
                        AdminFlashSale(
                            id = initial?.id ?: "new",
                            name = name.trim(),
                            price = price.toLong(),
                            qty = qty.toInt(),
                            description = desc.trim(),
                            imageRes = initial?.imageRes ?: R.drawable.ic_placeholder,
                            endsAtEpochMs = endTime
                        )
                    )
                },
                enabled = canSave,
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier.height(48.dp)
            ) {
                Text("Save", fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        },
        containerColor = Color(0xFF141414),
        titleContentColor = Color.White,
        textContentColor = Color.White.copy(alpha = 0.85f)
    )
}
