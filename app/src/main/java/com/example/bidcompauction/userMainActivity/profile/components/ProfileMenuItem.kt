package com.example.bidcompauction.userMainActivity.profile.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun ProfileMenuItem(
    icon: ImageVector,
    title: String,
    subtitle: String? = null,
    danger: Boolean = false,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = if (danger) Color(0xFFFF6B6B) else MaterialTheme.colorScheme.primary
        )

        Spacer(Modifier.width(14.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                title,
                color = if (danger) Color(0xFFFF6B6B) else Color.White,
                style = MaterialTheme.typography.bodyLarge
            )
            if (subtitle != null) {
                Spacer(Modifier.height(2.dp))
                Text(
                    subtitle,
                    color = Color.White.copy(alpha = 0.6f),
                    style = MaterialTheme.typography.labelMedium
                )
            }
        }

        Icon(
            imageVector = Icons.Filled.KeyboardArrowRight,
            contentDescription = null,
            tint = Color.White.copy(alpha = 0.5f)
        )
    }
}
