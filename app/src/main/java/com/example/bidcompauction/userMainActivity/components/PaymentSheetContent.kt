package com.example.bidcompauction.userMainActivity.components

import android.net.Uri // FIX: Gunakan android.net.Uri, bukan coil3.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.example.bidcompauction.userMainActivity.UserMainUtils.formatRupiah
import data.viewmodel.PaymentViewModel

@Composable
fun PaymentSheetContent(
    sourceId: Int,
    sourceType: String,
    title: String,
    price: Long,
    onDismiss: () -> Unit,
    paymentViewModel: PaymentViewModel = viewModel()
) {
    val context = LocalContext.current
    val isLoading by paymentViewModel.isLoading.collectAsState()

    // FIX: Tipe data harus android.net.Uri
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    // Launcher untuk Gallery
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
            .navigationBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Payment Instructions", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
        Text(title, color = Color.Gray, fontSize = 14.sp)

        Spacer(Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF252525)),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text("Transfer to Bank BCA", color = Color.Gray, fontSize = 12.sp)
                Text("8830 1234 567", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Black)
                Text("a/n PT BID COMP AUCTION", color = Color.White, fontSize = 14.sp)

                HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp), color = Color.DarkGray)

                Text("Total Amount: ${formatRupiah(price)}", color = Color(0xFF00FF85), fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(Modifier.height(24.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFF1A1A1A))
                .clickable { launcher.launch("image/*") },
            contentAlignment = Alignment.Center
        ) {
            if (selectedImageUri != null) {
                AsyncImage(
                    model = selectedImageUri,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.CloudUpload, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(32.dp))
                    Spacer(Modifier.height(8.dp))
                    Text("Tap to upload proof", color = Color.Gray, fontSize = 12.sp)
                }
            }
        }

        Spacer(Modifier.height(32.dp))

        Button(
            onClick = {
                selectedImageUri?.let { uri ->
                    paymentViewModel.uploadPayment(context, sourceType, sourceId, uri) {
                        onDismiss()
                    }
                }
            },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            enabled = selectedImageUri != null && !isLoading,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFD700))
        ) {
            if (isLoading) {
                // FIX: M3 CircularProgressIndicator menggunakan modifier untuk ukuran
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = Color.Black,
                    strokeWidth = 2.dp
                )
            } else {
                Text("CONFIRM PAYMENT", color = Color.Black, fontWeight = FontWeight.Bold)
            }
        }
    }
}