package com.example.bidcompauction.userMainActivity.UserMainUtils


import androidx.compose.runtime.*
import kotlinx.coroutines.delay
import java.text.NumberFormat
import java.util.Locale

@Composable
fun rememberRemainingTime(endsAtEpochMs: Long): State<Long> {
    val remaining = remember { mutableStateOf(endsAtEpochMs - System.currentTimeMillis()) }
    LaunchedEffect(endsAtEpochMs) {
        while (true) {
            remaining.value = endsAtEpochMs - System.currentTimeMillis()
            delay(1000)
        }
    }
    return remaining
}

fun formatCountdown(ms: Long): String {
    val totalSeconds = (ms / 1000).coerceAtLeast(0)
    val h = totalSeconds / 3600
    val m = (totalSeconds % 3600) / 60
    val s = totalSeconds % 60
    return "%02d:%02d:%02d".format(h, m, s)
}

fun formatRupiah(amount: Long): String {
    val nf = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
    return nf.format(amount).replace(",00", "")
}
