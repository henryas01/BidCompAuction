package com.example.bidcompauction.adminMainActivity

import java.text.NumberFormat
import java.util.Locale

fun formatRupiah(amount: Long): String {
    val nf = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
    return nf.format(amount).replace(",00", "")
}

fun formatCountdown(ms: Long): String {
    val sec = (ms / 1000).coerceAtLeast(0)
    val h = sec / 3600
    val m = (sec % 3600) / 60
    val s = sec % 60
    return "%02d:%02d:%02d".format(h, m, s)
}

