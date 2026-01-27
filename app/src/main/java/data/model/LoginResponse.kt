
package com.example.bidcompauction.data.model

import android.R

data class LoginResponse(
    val access_token: String,
    val user: User

)

data class User(
    val id: Int,
    val name: String,
    val email: String,
    val isAdmin: Boolean,
    val phone_number: String,
    val created_at: String,
    val updated_at: String
)
