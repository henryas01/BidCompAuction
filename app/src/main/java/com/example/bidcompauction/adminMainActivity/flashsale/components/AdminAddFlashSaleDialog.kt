package com.example.bidcompauction.adminMainActivity.flashsale.components

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import coil3.compose.AsyncImage
import com.example.bidcompauction.adminMainActivity.products.components.AdminTextField
import data.model.AdminFlashSaleRequest
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun AdminAddFlashSaleDialog(
    open: Boolean,
    onDismiss: () -> Unit,
    onSave: (AdminFlashSaleRequest) -> Unit
) {
    if (!open) return

    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val scrollState = rememberScrollState()

    // State Form
    var name by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var qty by remember { mutableStateOf("") }
    var desc by remember { mutableStateOf("") }

    // State Date & Time
    var startDate by remember { mutableStateOf(Date()) }
    var endDate by remember { mutableStateOf(Date(System.currentTimeMillis() + 3600_000)) }

    val displayFormatter = remember { SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault()) }
    val isoFormatter = remember {
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }
    }

    // State Image Picker
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? -> selectedImageUri = uri }

    // Helper Picker
    fun showDateTimePicker(currentDate: Date, onUpdate: (Date) -> Unit) {
        focusManager.clearFocus()
        val cal = Calendar.getInstance().apply { time = currentDate }
        DatePickerDialog(context, { _, y, m, d ->
            TimePickerDialog(context, { _, hh, mm ->
                val result = Calendar.getInstance().apply { set(y, m, d, hh, mm) }
                onUpdate(result.time)
            }, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show()
    }

    AlertDialog(
        onDismissRequest = {
            focusManager.clearFocus()
            onDismiss()
        },
        properties = DialogProperties(usePlatformDefaultWidth = false),
        modifier = Modifier
            .fillMaxWidth(0.95f)
            .padding(vertical = 20.dp),
        containerColor = Color(0xFF141414),
        title = {
            Text("Create Flash Sale", color = Color.White, fontWeight = FontWeight.Black)
        },
        text = {
            val focusManager = LocalFocusManager.current
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 450.dp) // Membatasi tinggi agar stabil
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
                    // --- IMAGE PICKER ---
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
                            .clickable {
                                focusManager.clearFocus()
                                launcher.launch("image/*")
                            },
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

                    // --- INPUT FIELDS ---
                    AdminTextField(value = name, onValueChange = { name = it }, label = "Product Name")

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        Box(modifier = Modifier.weight(1f)) {
                            AdminTextField(value = price, onValueChange = { price = it }, label = "Flash Price", isNumber = true)
                        }
                        Box(modifier = Modifier.weight(0.6f)) {
                            AdminTextField(value = qty, onValueChange = { qty = it }, label = "Flash Qty", isNumber = true)
                        }
                    }

                    // --- USER FRIENDLY DATE PICKER ---
                    DateTimeDisplayItem(label = "Starts At", value = displayFormatter.format(startDate)) {
                        showDateTimePicker(startDate) { startDate = it }
                    }

                    DateTimeDisplayItem(label = "Ends At", value = displayFormatter.format(endDate)) {
                        showDateTimePicker(endDate) { endDate = it }
                    }

                    AdminTextField(value = desc, onValueChange = { desc = it }, label = "Description", isMultiLine = true)

                    // Spacer agar input terakhir tidak tertutup keyboard
                    Spacer(modifier = Modifier.height(150.dp))
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    focusManager.clearFocus()
                    onSave(AdminFlashSaleRequest(
                        name = name,
                        price = price.toLongOrNull() ?: 0L,
                        stock = qty.toIntOrNull() ?: 0,
                        desc = desc,
                        startAt = isoFormatter.format(startDate),
                        endAt = isoFormatter.format(endDate),
                        imageUri = selectedImageUri
                    ))
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFD700), contentColor = Color.Black),
                modifier = Modifier.fillMaxWidth().height(48.dp),
                shape = RoundedCornerShape(12.dp),
                enabled = name.isNotBlank() && price.isNotBlank() && selectedImageUri != null
            ) {
                Text("LAUNCH FLASH SALE", fontWeight = FontWeight.Black)
            }
        },
        dismissButton = {
            TextButton(onClick = {
                focusManager.clearFocus()
                onDismiss()
            }) {
                Text("Cancel", color = Color.Gray)
            }
        }
    )
}
