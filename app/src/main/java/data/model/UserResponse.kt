package com.example.bidcompauction.data.model

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("phone_number")
    val phoneNumber: String,

    @SerializedName("created_at")
    val createdAt: String,

    @SerializedName("updated_at")
    val updatedAt: String
)