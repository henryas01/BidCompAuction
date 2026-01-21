package com.example.bidcompauction.userMainActivity.notification

data class NotificationUi(
    val id: String,
    val title: String,
    val message: String,
    val time: String,
    val unread: Boolean,
    val type: NotificationType
)

enum class NotificationType {
    ORDER, PROMO, SYSTEM
}
