package com.example.bidcompauction.adminMainActivity.products.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import coil3.compose.AsyncImage
import data.model.AdminProductRequest
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager

@Composable
fun AdminAddProductDialog(
    open: Boolean,
    onDismiss: () -> Unit,
    onSave: (AdminProductRequest) -> Unit
) {
    if (!open) return

    val scrollState = rememberScrollState()
    var name by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var stock by remember { mutableStateOf("") }
    var desc by remember { mutableStateOf("") }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? -> selectedImageUri = uri }

    AlertDialog(
        onDismissRequest = onDismiss,
        // Properti ini mencegah dialog "melompat" saat mencoba re-center
        properties = DialogProperties(
            usePlatformDefaultWidth = false // Memungkinkan kita mengatur lebar sendiri
        ),
        modifier = Modifier
            .fillMaxWidth(0.95f) // Atur lebar dialog agar tidak mentok layar
            .padding(vertical = 20.dp),
        containerColor = Color(0xFF141414),
        title = {
            Text("Add New Product", color = Color.White, fontWeight = FontWeight.Black)
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
                        .verticalScroll(scrollState)
                        .pointerInput(Unit) {
                            detectTapGestures(onTap = {
                                focusManager.clearFocus()
                            })
                        },
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // --- IMAGE BOX ---
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(160.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color(0xFF1F1F1F))
                            .pointerInput(Unit) {
                                detectTapGestures(onTap = {
                                    focusManager.clearFocus()
                                })
                            }
                            .clickable { launcher.launch("image/*") },
                        contentAlignment = Alignment.Center
                    ) {
                        if (selectedImageUri != null) {
                            AsyncImage(
                                model = selectedImageUri,
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                        } else {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(Icons.Default.CloudUpload, null, tint = Color(0xFFFFD700), modifier = Modifier.size(32.dp))
                                Text("Upload Image", color = Color.White, fontSize = 12.sp)
                            }
                        }
                    }

                    // --- INPUTS ---
                    AdminTextField(value = name, onValueChange = { name = it }, label = "Product Name")

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        Box(modifier = Modifier.weight(1f)) {
                            AdminTextField(value = price, onValueChange = { price = it }, label = "Price", isNumber = true)
                        }
                        Box(modifier = Modifier.weight(0.6f)) {
                            AdminTextField(value = stock, onValueChange = { stock = it }, label = "Stock", isNumber = true)
                        }
                    }

                    AdminTextField(value = desc, onValueChange = { desc = it }, label = "Description", isMultiLine = true)

                    // Spacer sangat penting agar input terakhir bisa di-scroll ke atas keyboard
                    Spacer(modifier = Modifier.height(150.dp))
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onSave(AdminProductRequest(name, price, stock, desc, selectedImageUri))
                    onDismiss()
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFD700)),
                modifier = Modifier.fillMaxWidth().height(48.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("ADD PRODUCT", color = Color.Black, fontWeight = FontWeight.Black)
            }
        }
    )
}