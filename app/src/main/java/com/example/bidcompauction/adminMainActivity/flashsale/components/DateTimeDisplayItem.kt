package com.example.bidcompauction.adminMainActivity.flashsale.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun DateTimeDisplayItem(label: String, value: String, onClick: () -> Unit) {
    Column(modifier = Modifier.clickable { onClick() }) {
        Text(label, color = Color.Gray, fontSize = 12.sp, modifier = Modifier.padding(bottom = 4.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF1F1F1F), RoundedCornerShape(12.dp))
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(value, color = Color.White, fontSize = 14.sp)
            Icon(Icons.Default.CalendarMonth, null, tint = Color(0xFFFFD700), modifier = Modifier.size(20.dp))
        }
    }
}