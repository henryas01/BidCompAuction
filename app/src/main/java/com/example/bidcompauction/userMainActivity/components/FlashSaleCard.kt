package com.example.bidcompauction.userMainActivity.components




import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.bidcompauction.userMainActivity.FlashSaleUi
import com.example.bidcompauction.userMainActivity.UserMainUtils.formatCountdown
import com.example.bidcompauction.userMainActivity.UserMainUtils.formatRupiah
import com.example.bidcompauction.userMainActivity.UserMainUtils.rememberRemainingTime

@Composable
fun FlashSaleCard(
    item: FlashSaleUi,
    onAdd: (FlashSaleUi) -> Unit = {}
) {
    val remainingMs by rememberRemainingTime(item.endsAtEpochMs)
    val ended = remainingMs <= 0L
    val countdown = if (ended) "Ended" else formatCountdown(remainingMs)

    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF101010)),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = Modifier
            .width(300.dp)
            .height(180.dp)
    ) {
        Row(Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .width(130.dp)
                    .fillMaxHeight()
                    .padding(12.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFF0D0D0D))
            ) {
                Image(
                    painter = painterResource(id = item.imageRes),
                    contentDescription = item.name,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.fillMaxSize().padding(8.dp)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(top = 12.dp, end = 12.dp, bottom = 12.dp)
                    .padding(start = 2.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        item.name,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(Modifier.height(6.dp))

                    Row {
                        Text(
                            formatRupiah(item.salePrice),
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.ExtraBold
                        )
                        Spacer(Modifier.width(10.dp))
                        Text(
                            formatRupiah(item.originalPrice),
                            color = Color.White.copy(alpha = 0.5f),
                            style = MaterialTheme.typography.labelMedium
                        )
                    }

                    Spacer(Modifier.height(8.dp))

                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(14.dp))
                            .background(if (ended) Color(0x22FFFFFF) else Color(0x22FF3D00))
                            .padding(horizontal = 10.dp, vertical = 6.dp),
                        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Bolt,
                            contentDescription = "Flash",
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(Modifier.width(6.dp))
                        Text(
                            countdown,
                            color = Color.White,
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
                ) {
                    Text(
                        "Qty: ${item.qty}",
                        color = Color.White.copy(alpha = 0.75f),
                        style = MaterialTheme.typography.labelLarge
                    )

                    FilledTonalButton(
                        onClick = { onAdd(item) },
                        enabled = !ended && item.qty > 0,
                        colors = ButtonDefaults.filledTonalButtonColors(
                            containerColor = Color(0x22FFFFFF),
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(14.dp),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp)
                    ) {
                        Icon(Icons.Filled.AddShoppingCart, contentDescription = "Add", modifier = Modifier.size(18.dp))
                        Spacer(Modifier.width(6.dp))
                        Text("Add", fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }
    }
}
