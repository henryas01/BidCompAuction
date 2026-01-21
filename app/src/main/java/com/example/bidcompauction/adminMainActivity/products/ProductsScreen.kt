package com.example.bidcompauction.adminMainActivity.products

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.bidcompauction.R
import com.example.bidcompauction.adminMainActivity.components.ConfirmDeleteDialog
import com.example.bidcompauction.adminMainActivity.products.components.ItemCard
import com.example.bidcompauction.adminMainActivity.products.components.AdminProductDialog

@Composable
fun ProductsScreen(
    search: String
) {
    var products by remember {
        mutableStateOf(
            listOf(
                AdminProduct(
                    "1",
                    "RTX 3060 Ti",
                    4200000,
                    8,
                    "High performance GPU",
                    R.drawable.ic_placeholder
                )
            )
        )
    }

    var editItem by remember { mutableStateOf<AdminProduct?>(null) }
    var deleteItem by remember { mutableStateOf<AdminProduct?>(null) }

    val filtered = remember(search, products) {
        if (search.isBlank()) products
        else products.filter { it.name.contains(search, true) }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        items(filtered, key = { it.id }) { product ->
            ItemCard(
                product = product,
                onEdit = { editItem = product },
                onDelete = { deleteItem = product }
            )
        }
    }

    AdminProductDialog(
        open = editItem != null,
        initial = editItem,
        onDismiss = { editItem = null },
        onSave = { saved ->
            products =
                if (products.any { it.id == saved.id })
                    products.map { if (it.id == saved.id) saved else it }
                else
                    products + saved
            editItem = null
        }
    )

    ConfirmDeleteDialog(
        open = deleteItem != null,
        onDismiss = { deleteItem = null },
        onConfirm = {
            products = products.filterNot { it.id == deleteItem?.id }
            deleteItem = null
        }
    )
}
