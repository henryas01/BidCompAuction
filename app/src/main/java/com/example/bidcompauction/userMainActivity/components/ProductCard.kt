package com.example.bidcompauction.userMainActivity.components


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.bidcompauction.userMainActivity.ProductUi
import com.example.bidcompauction.userMainActivity.UserMainUtils.formatRupiah

@Composable
fun ProductCard(
    p: ProductUi,
    onAdd: (ProductUi) -> Unit = {}
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF101010)),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(260.dp)
    ) {
        Column(Modifier.fillMaxSize().padding(12.dp)) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .clip(RoundedCornerShape(18.dp))
                    .background(Color(0xFF0D0D0D))
            ) {
                Image(
                    painter = painterResource(id = p.imageRes),
                    contentDescription = p.name,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.fillMaxSize().padding(10.dp)
                )

                // qty badge
                Box(
                    modifier = Modifier
                        .align(androidx.compose.ui.Alignment.TopEnd)
                        .padding(10.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(Color(0x22FFFFFF))
                        .padding(horizontal = 10.dp, vertical = 6.dp)
                ) {
                    Text("Qty ${p.qty}", style = MaterialTheme.typography.labelMedium, color = Color.White)
                }
            }

            Spacer(Modifier.height(10.dp))

            Text(
                p.name,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(Modifier.height(6.dp))

            Text(
                formatRupiah(p.price),
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(Modifier.weight(1f))

            Button(
                onClick = { onAdd(p) },
                enabled = p.qty > 0,
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    disabledContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.35f),
                    disabledContentColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.6f)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp)
                    .shadow(
                        elevation = 8.dp,
                        shape = RoundedCornerShape(16.dp),
                        ambientColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.25f),
                        spotColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.25f)
                    )
            ) {
                Icon(Icons.Filled.AddShoppingCart, contentDescription = "Add", modifier = Modifier.size(18.dp))
                Spacer(Modifier.width(8.dp))
                Text("Add", fontWeight = FontWeight.Bold)
            }
        }
    }
}
