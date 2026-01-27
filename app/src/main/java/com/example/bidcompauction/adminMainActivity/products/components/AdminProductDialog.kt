package com.example.bidcompauction.adminMainActivity.products.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import data.model.AdminProductResponse

import androidx.compose.ui.window.DialogProperties
import utils.Constants.BASE_URL

@Composable
fun AdminProductDialog(
    open: Boolean,
    initial: AdminProductResponse?,
    onDismiss: () -> Unit,
    onSave: (id: Int, name: String, price: Long, stock: Int, desc: String) -> Unit
) {
    if (!open) return

    // State untuk scroll yang stabil
    val scrollState = rememberScrollState()

    var name by remember(initial) { mutableStateOf(initial?.name ?: "") }
    var price by remember(initial) { mutableStateOf(initial?.price?.toString() ?: "") }
    var stock by remember(initial) { mutableStateOf(initial?.stock?.toString() ?: "") }
    var desc by remember(initial) { mutableStateOf(initial?.desc ?: "") }



    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? -> selectedImageUri = uri }

    AlertDialog(
        onDismissRequest = onDismiss,
        // Properti ini mencegah dialog mencoba memusatkan diri secara agresif saat keyboard muncul
        properties = DialogProperties(usePlatformDefaultWidth = false),
        modifier = Modifier
            .fillMaxWidth(0.95f)
            .padding(vertical = 16.dp),
        containerColor = Color(0xFF141414),
        title = {
            Text(
                text = if (initial == null) "Create Product" else "Modify Product",
                color = Color.White, fontWeight = FontWeight.Black, fontSize = 20.sp
            )
        },
        text = {
            val focusManager = LocalFocusManager.current
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 400.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .pointerInput(Unit) {
                            detectTapGestures(onTap = {
                                focusManager.clearFocus()
                            })
                        }
                        .verticalScroll(scrollState),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // --- IMAGE PICKER ---
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(160.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color(0xFF1F1F1F))
                            .clickable { launcher.launch("image/*") },
                        contentAlignment = Alignment.Center
                    ) {
                        val imageUrl = if (selectedImageUri != null) {
                            selectedImageUri
                        } else if (initial != null && initial.images.isNotEmpty()) {
                            "$BASE_URL${initial.images.first()}"
                        } else {
                            null
                        }

                        if (imageUrl != null) {
                            AsyncImage(
                                model = imageUrl,
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                        }

                        Box(
                            modifier = Modifier.fillMaxSize().background(Color.Black.copy(0.3f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(Icons.Default.CloudUpload, null, tint = Color(0xFFFFD700), modifier = Modifier.size(32.dp))
                                Text("Change Image", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }

                    // --- INPUT FIELDS ---
                    AdminTextField(value = name, onValueChange = { name = it }, label = "Product Name")

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        Box(modifier = Modifier.weight(1.5f)) {
                            AdminTextField(value = price, onValueChange = { price = it }, label = "Price", isNumber = true)
                        }
                        Box(modifier = Modifier.weight(1f)) {
                            AdminTextField(value = stock, onValueChange = { stock = it }, label = "Stock", isNumber = true)
                        }
                    }

                    AdminTextField(value = desc, onValueChange = { desc = it }, label = "Description", isMultiLine = true)

                    // --- SPACER KUNCI ---
                    // Menambahkan ruang kosong di bawah agar field 'Description'
                    // bisa di-scroll ke atas keyboard tanpa mendorong dialog
                    Spacer(modifier = Modifier.height(180.dp))
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    initial?.id?.let {
                        onSave(it, name, price.toLongOrNull() ?: 0L, stock.toIntOrNull() ?: 0, desc)
                    }
                    onDismiss()
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFD700)),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth().height(48.dp),
                enabled = name.isNotBlank() && price.isNotBlank()
            ) {
                Text("SAVE CHANGES", color = Color.Black, fontWeight = FontWeight.Black)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = Color.Gray)
            }
        }
    )
}
@Composable
fun AdminTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isNumber: Boolean = false,
    isMultiLine: Boolean = false
) {

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(
            keyboardType = if (isNumber) KeyboardType.Number else KeyboardType.Text,
            imeAction = if (isMultiLine) ImeAction.Default else ImeAction.Next
        ),
        singleLine = !isMultiLine,
        minLines = if (isMultiLine) 3 else 1,
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0xFFFFD700),
            unfocusedBorderColor = Color(0xFF333333),
            focusedLabelColor = Color(0xFFFFD700),
            cursorColor = Color(0xFFFFD700),
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White
        )
    )
}