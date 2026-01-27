
package com.example.bidcompauction.data.model

import com.google.gson.annotations.SerializedName

data class SignupRequest(
    @SerializedName("name")
    val name: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("phone_number")
    val phoneNumber: String,

    @SerializedName("password")
    val password: String,
)