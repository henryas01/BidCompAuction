package com.example.bidcompauction.adminMainActivity.products

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bidcompauction.adminMainActivity.components.ConfirmDeleteDialog
import com.example.bidcompauction.adminMainActivity.products.components.AdminAddProductDialog
import com.example.bidcompauction.adminMainActivity.products.components.ItemCard
import com.example.bidcompauction.adminMainActivity.products.components.AdminProductDialog
import data.model.AdminProductResponse
import data.viewmodel.ProductViewModel

@Composable
fun ProductsScreen(
    search: String,
    viewModel: ProductViewModel = viewModel()
) {

    val context = androidx.compose.ui.platform.LocalContext.current
    val products by viewModel.products.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    var editItem by remember { mutableStateOf<AdminProductResponse?>(null) }
    var deleteItem by remember { mutableStateOf<AdminProductResponse?>(null) }
    var showAddDialog by remember { mutableStateOf(false) }

    val filtered = remember(search, products) {
        if (search.isBlank()) products
        else products.filter { it.name.contains(search, true) }
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
                Icon(Icons.Default.Add, contentDescription = "Add Product")
            }
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            if (isLoading && products.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Color(0xFFFFD700))
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    // Menambahkan tipe eksplisit pada key untuk menghindari infer error
                    items(filtered, key = { item: AdminProductResponse -> item.id }) { product ->
                        ItemCard(
                            product = product,
                            onEdit = { editItem = product },
                            onDelete = { deleteItem = product }
                        )
                    }
                }
            }
        }

        // --- Dialog Add Product ---
        // Diperbarui: Sekarang menerima satu objek AdminProductRequest
        AdminAddProductDialog(
            open = showAddDialog,
            onDismiss = { showAddDialog = false },
            onSave = { request ->
                viewModel.addProduct(
                    context = context,
                    name = request.name,
                    price = request.price.toLong(),
                    stock = request.stock.toInt(),
                    desc = request.desc,
                    imageUri = request.imageUri // URI gambar dari picker
                )
                showAddDialog = false
            }
        )

        // --- Dialog Edit Product ---
        AdminProductDialog(
            open = editItem != null,
            initial = editItem,
            onDismiss = { editItem = null },
            // FIX: Tambahkan parameter imageUri (uri) di sini
            onSave = { updatedId, name, price, stock, desc, uri ->
                viewModel.updateProduct(
                    context = context, // Kirim context
                    id = updatedId,
                    name = name,
                    price = price,
                    stock = stock,
                    desc = desc,
                    imageUri = uri // Kirim uri yang baru dipilih (bisa null jika tidak ganti)
                )
                editItem = null
            }
        )

        // --- Dialog Confirm Delete ---
        ConfirmDeleteDialog(
            open = deleteItem != null,
            onDismiss = { deleteItem = null },
            onConfirm = {
                deleteItem?.let { viewModel.deleteProduct(it.id) }
                deleteItem = null
            }
        )
    }
}