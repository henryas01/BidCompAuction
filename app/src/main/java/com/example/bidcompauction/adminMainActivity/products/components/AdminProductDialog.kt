package com.example.bidcompauction.adminMainActivity.products.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.bidcompauction.R
import com.example.bidcompauction.adminMainActivity.products.AdminProduct // adjust if your model package is different

@Composable
fun AdminProductDialog(
    open: Boolean,
    initial: AdminProduct?,
    onDismiss: () -> Unit,
    onSave: (AdminProduct) -> Unit
) {
    if (!open) return

    var name by remember(open) { mutableStateOf(initial?.name ?: "") }
    var price by remember(open) { mutableStateOf(initial?.price?.toString() ?: "") }
    var qty by remember(open) { mutableStateOf(initial?.qty?.toString() ?: "") }
    var desc by remember(open) { mutableStateOf(initial?.description ?: "") }

    val canSave =
        name.isNotBlank() &&
                price.toLongOrNull() != null &&
                qty.toIntOrNull() != null

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = if (initial == null) "Add Product" else "Edit Product",
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") },
                    singleLine = true,
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
                    label = { Text("Qty") },
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
                    text = "Image: placeholder for now (add picker later).",
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.White.copy(alpha = 0.6f)
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onSave(
                        AdminProduct(
                            id = initial?.id ?: "new",
                            name = name.trim(),
                            price = price.toLong(),
                            qty = qty.toInt(),
                            description = desc.trim(),
                            imageRes = initial?.imageRes ?: R.drawable.ic_placeholder
                        )
                    )
                },
                enabled = canSave,
                shape = RoundedCornerShape(14.dp)
            ) {
                Text("Save", fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        },
        containerColor = Color(0xFF141414),
        titleContentColor = Color.White,
        textContentColor = Color.White.copy(alpha = 0.85f)
    )
}
