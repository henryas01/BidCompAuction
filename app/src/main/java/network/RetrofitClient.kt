//package data.network

package com.example.bidcompauction.network

import com.example.bidcompauction.network.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    // 192.168.1.4 ipconfig getifaddr en0
    // 172.20.10.3
    // http://10.0.2.2:3000/
    private const val BASE_URL = "http://192.168.1.10:3000/" // ← for Development

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}

