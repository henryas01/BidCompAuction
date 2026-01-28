package com.example.bidcompauction.adminMainActivity.flashsale

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.bidcompauction.R
import com.example.bidcompauction.adminMainActivity.components.ConfirmDeleteDialog
import com.example.bidcompauction.adminMainActivity.components.FlashSaleItemCard
import com.example.bidcompauction.adminMainActivity.flashsale.components.AdminAddFlashSaleDialog // Import Dialog Tambah
import com.example.bidcompauction.adminMainActivity.flashsale.components.AdminFlashSaleDialog
import kotlinx.coroutines.delay
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import data.model.AdminFlashSaleResponse
import data.viewmodel.FlashSaleViewModel

import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

@Composable
fun FlashSaleScreen(
    search: String,
    flashSaleViewModel: FlashSaleViewModel = viewModel()
) {
    val context = LocalContext.current
    val items by flashSaleViewModel.items.collectAsState()
    val isLoading by flashSaleViewModel.isLoading.collectAsState()

    var showAddDialog by remember { mutableStateOf(false) }
    var editItem by remember { mutableStateOf<AdminFlashSaleResponse?>(null) }
    var deleteItem by remember { mutableStateOf<AdminFlashSaleResponse?>(null) }

    // Helper untuk parsing ISO Date yang aman untuk API 25
    val isoFormatter = remember {
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }
    }

    var now by remember { mutableLongStateOf(System.currentTimeMillis()) }
    LaunchedEffect(Unit) {
        while (true) {
            delay(1_000)
            now = System.currentTimeMillis()
        }
    }

    Scaffold(
        containerColor = Color.Transparent,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true },
                containerColor = Color(0xFFFFD700),
                contentColor = Color.Black,
                shape = RoundedCornerShape(16.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Flash Sale")
            }
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding)) {
            if (isLoading && items.isEmpty()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center), color = Color(0xFFFFD700))
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    val filteredItems = items.filter { it.name.contains(search, ignoreCase = true) }

                    items(filteredItems, key = { it.id }) { item ->
                        // FIX ISSUE #1: Parsing manual untuk API 25
                        val endTimeMs = try {
                            isoFormatter.parse(item.endAt)?.time ?: 0L
                        } catch (e: Exception) { 0L }

                        val remainingMs = endTimeMs - now

                        // FIX ISSUE #2: Pastikan FlashSaleItemCard menerima AdminFlashSaleResponse
                        FlashSaleItemCard(
                            item = item,
                            remainingMs = remainingMs,
                            onEdit = { editItem = item },
                            onDelete = { deleteItem = item }
                        )
                    }
                }
            }
        }

        // --- DIALOG SECTION ---

        // A. Dialog Tambah
        if (showAddDialog) {
            AdminAddFlashSaleDialog(
                open = showAddDialog,
                onDismiss = { showAddDialog = false },
                onSave = { request ->
                    // FIX ISSUE #3: Pastikan onSave di dialog ini mengirim AdminFlashSaleRequest
                    flashSaleViewModel.addFlashSale(request)
                    showAddDialog = false
                }
            )
        }

        // B. Dialog Edit
        if (editItem != null) {
            AdminFlashSaleDialog(
                open = editItem != null,
                initial = editItem,
                onDismiss = { editItem = null },
                onSave = { updatedData, newImageUri ->
                    flashSaleViewModel.updateFlashSale(
                        context = context,
                        id = updatedData.id,
                        name = updatedData.name,
                        price = updatedData.price.toString().filter { it.isDigit() }.toLongOrNull() ?: 0L,
                        stock = updatedData.stock,
                        desc = updatedData.descriptions ?: "",
                        startAt = updatedData.startAt,
                        endAt = updatedData.endAt,
                        imageUri = newImageUri
                    )
                    editItem = null
                }
            )
        }

        // C. Dialog Hapus
        ConfirmDeleteDialog(
            open = deleteItem != null,
            onDismiss = { deleteItem = null },
            onConfirm = {
                deleteItem?.let { flashSaleViewModel.deleteFlashSale(it.id) }
                deleteItem = null
            }
        )
    }
}