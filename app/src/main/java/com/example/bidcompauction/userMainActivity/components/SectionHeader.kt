package com.example.bidcompauction.userMainActivity.components


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun SectionHeader(
    title: String,
    subtitle: String,
    actionText: String = "See all",
    onAction: () -> Unit = {}
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = androidx.compose.ui.Alignment.Bottom
    ) {
        Column {
            Text(title, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Text(
                subtitle,
                style = MaterialTheme.typography.labelMedium,
                color = Color.White.copy(alpha = 0.7f)
            )
        }
        AssistChip(
            onClick = onAction,
            label = { Text(actionText) },
            colors = AssistChipDefaults.assistChipColors(
                containerColor = Color(0x22FFFFFF),
                labelColor = Color.White
            )
        )
    }
    Spacer(Modifier.height(10.dp))
}
