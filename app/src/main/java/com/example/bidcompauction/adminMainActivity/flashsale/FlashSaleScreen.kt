package com.example.bidcompauction.adminMainActivity.flashsale

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.bidcompauction.R
import com.example.bidcompauction.adminMainActivity.components.ConfirmDeleteDialog
import com.example.bidcompauction.adminMainActivity.components.FlashSaleItemCard
import com.example.bidcompauction.adminMainActivity.flashsale.components.AdminFlashSaleDialog
import kotlinx.coroutines.delay

@Composable
fun FlashSaleScreen(search: String) {

    var items by remember {
        mutableStateOf(
            listOf(
                AdminFlashSale(
                    "1",
                    "RTX 4070 SUPER",
                    9999000,
                    5,
                    "Limited flash sale",
                    R.drawable.ic_placeholder,
                    System.currentTimeMillis() + 30 * 60_000
                )
            )
        )
    }

    var editItem by remember { mutableStateOf<AdminFlashSale?>(null) }
    var deleteItem by remember { mutableStateOf<AdminFlashSale?>(null) }

    var now by remember { mutableStateOf(System.currentTimeMillis()) }
    LaunchedEffect(Unit) {
        while (true) {
            delay(1_000)
            now = System.currentTimeMillis()
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(items, key = { it.id }) { item ->

            val remainingMs = item.endsAtEpochMs - now

            FlashSaleItemCard(
                item = item,
                remainingMs = remainingMs,
                onEdit = {
                    editItem = item
                },
                onDelete = {
                    deleteItem = item
                }
            )
        }
    }

    AdminFlashSaleDialog(
        open = editItem != null,
        initial = editItem,
        onDismiss = { editItem = null },
        onSave = { saved ->
            items = items.map { if (it.id == saved.id) saved else it }
            editItem = null
        }
    )

    ConfirmDeleteDialog(
        open = deleteItem != null,
        onDismiss = { deleteItem = null },
        onConfirm = {
            items = items.filterNot { it.id == deleteItem?.id }
            deleteItem = null
        }
    )
}
