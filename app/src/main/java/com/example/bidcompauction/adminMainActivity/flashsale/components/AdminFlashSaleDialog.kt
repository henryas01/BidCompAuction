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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import coil3.compose.AsyncImage
import com.example.bidcompauction.R
import com.example.bidcompauction.adminMainActivity.products.components.AdminTextField
import data.model.AdminFlashSaleResponse
import utils.Constants
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun AdminFlashSaleDialog(
    open: Boolean,
    initial: AdminFlashSaleResponse?,
    onDismiss: () -> Unit,
    onSave: (AdminFlashSaleResponse, Uri?) -> Unit
) {
    if (!open || initial == null) return

    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val scrollState = rememberScrollState()

    // State Form
    var name by remember(initial) { mutableStateOf(initial.name) }
    var price by remember(initial) { mutableStateOf(initial.price.toString()) }
    var qty by remember(initial) { mutableStateOf(initial.stock.toString()) }
    var descriptions by remember(initial) { mutableStateOf(initial.descriptions) }

    // Formatter & Date State
    // --- Formatter Section ---
    val displayFormatter = remember {
        SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())
    }

// Gunakan format yang mendukung standar ISO8601 dari server
// Biasanya API modern mengirimkan format: 2026-01-26T10:00:00.000Z atau 2026-01-26T10:00:00Z
    val isoFormatter = remember {
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }
    }

    // Helper untuk parsing aman (Menangani jika ada milidetik atau tidak)
    fun parseIsoDate(isoString: String?): Date {
        if (isoString.isNullOrEmpty()) return Date()
        return try {
            // Coba hilangkan 'Z' dan milidetik jika ada untuk parsing manual yang stabil
            val cleanString = isoString.replace("Z", "").split(".")[0]
            isoFormatter.parse(cleanString) ?: Date()
        } catch (e: Exception) {
            Date()
        }
    }

    // --- State Section ---
    // Gunakan key 'initial' di remember agar state reset saat data dari API berubah
    var startDate by remember(initial) {
        mutableStateOf(parseIsoDate(initial?.startAt))
    }

    var endDate by remember(initial) {
        mutableStateOf(parseIsoDate(initial?.endAt))
    }

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
            Text("Edit Flash Sale", color = Color.White, fontWeight = FontWeight.Black)
        },
        text = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 450.dp)
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
                    // --- IMAGE PREVIEW/PICKER ---
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(160.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color(0xFF1F1F1F))
                            .clickable {
                                focusManager.clearFocus()
                                launcher.launch("image/*")
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        val imageUrl = if (!initial.images.isNullOrEmpty()) {
                            Constants.getFullImageUrl(initial.images.lastOrNull())
                        } else {
                            null
                        }

                        AsyncImage(
                            model = selectedImageUri ?: imageUrl,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize(),
                            error = painterResource(R.drawable.ic_placeholder),
                            placeholder = painterResource(R.drawable.ic_placeholder)
                        )

                        // Overlay icon upload agar user tahu bisa diklik
                        Box(
                            modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.CloudUpload, null, tint = Color(0xFFFFD700).copy(alpha = 0.8f))
                        }
                    }

                    // --- INPUT FIELDS ---
                    AdminTextField(value = name, onValueChange = { name = it }, label = "Product Name")

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        Box(modifier = Modifier.weight(1f)) {
                            AdminTextField(value = price, onValueChange = { price = it }, label = "Price", isNumber = true)
                        }
                        Box(modifier = Modifier.weight(0.6f)) {
                            AdminTextField(value = qty, onValueChange = { qty = it }, label = "Stock", isNumber = true)
                        }
                    }

                    // --- DATE PICKERS ---
                    DateTimeDisplayItem(label = "Starts At", value = displayFormatter.format(startDate)) {
                        showDateTimePicker(startDate) { startDate = it }
                    }

                    DateTimeDisplayItem(label = "Ends At", value = displayFormatter.format(endDate)) {
                        showDateTimePicker(endDate) { endDate = it }
                    }

                    AdminTextField(value = descriptions ?:"", onValueChange = { descriptions = it }, label = "Description", isMultiLine = true)

                    // Spacer agar tidak tertutup keyboard
                    Spacer(modifier = Modifier.height(150.dp))
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    focusManager.clearFocus()
                    val updatedData = initial.copy(
                        name = name,
                        price = price,
                        stock = qty.toIntOrNull() ?: 0,
                        descriptions = descriptions,
                        startAt = isoFormatter.format(startDate) + "Z",
                        endAt = isoFormatter.format(endDate) + "Z"
                    )
                    onSave(updatedData, selectedImageUri)
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFD700), contentColor = Color.Black),
                modifier = Modifier.fillMaxWidth().height(48.dp),
                shape = RoundedCornerShape(12.dp),
                enabled = name.isNotBlank() && price.isNotBlank()
            ) {
                Text("UPDATE FLASH SALE", fontWeight = FontWeight.Black)
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