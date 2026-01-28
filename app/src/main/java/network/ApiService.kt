package com.example.bidcompauction.network

import com.example.bidcompauction.data.model.BidsRequest
import com.example.bidcompauction.data.model.BidsResponse
import com.example.bidcompauction.data.model.LoginRequest
import com.example.bidcompauction.data.model.LoginResponse
import com.example.bidcompauction.data.model.SignupRequest
import com.example.bidcompauction.data.model.SignupResponse
import data.model.AdminFlashSaleResponse
import data.model.AdminProductResponse
import data.model.DeleteResponse
import data.model.PaymentResponse
import data.model.UserPaymentInvoiceResponse
import data.viewmodel.PaymentValidationResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService {

    // ---------- AUTH ----------
    @POST("api/auth/login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<LoginResponse>

    @POST("api/users/signup")
    suspend fun signupUser(
        @Body request: SignupRequest
    ): Response<SignupResponse>


    // ---------- PRODUCT ----------

    @GET("api/product")
    suspend fun getProducts(@Header("Authorization") token: String): List<AdminProductResponse>

    @Multipart
    @POST("api/product")
    suspend fun addProduct(
        @Header("Authorization") token: String,
        @Part("name") name: RequestBody,
        @Part("price") price: RequestBody,
        @Part("stock") stock: RequestBody,
        @Part("desc") desc: RequestBody,
        @Part image: MultipartBody.Part
    ): AdminProductResponse

    @Multipart
    @PATCH("api/product/{id}")
    suspend fun updateProduct(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Part("name") name: RequestBody,
        @Part("price") price: RequestBody,
        @Part("stock") stock: RequestBody,
        @Part("desc") desc: RequestBody,
        @Part image: MultipartBody.Part? // Gunakan tanda tanya (?) agar opsional
    ): Response<AdminProductResponse>

    @DELETE("api/product/{id}")
    suspend fun deleteProduct(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): DeleteResponse

    // ---------- FLASH SALE ----------
    @GET("api/flashsale")
    suspend fun getFlashSales(
        @Header("Authorization") token: String
    ): List<AdminFlashSaleResponse>

    @GET("api/flashsale/{id}")
    suspend fun getFlashSaleById(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): AdminFlashSaleResponse

    @Multipart
    @POST("api/flashsale")
    suspend fun addFlashSale(
        @Header("Authorization") token: String,
        @Part("name") name: RequestBody,
        @Part("price") price: RequestBody,
        @Part("stock") stock: RequestBody,
        @Part("desc") desc: RequestBody,
        @Part("startAt") startAt: RequestBody,
        @Part("endAt") endAt: RequestBody,
        @Part image: MultipartBody.Part
    ): AdminFlashSaleResponse

    @Multipart
    @PATCH("api/flashsale/{id}")
    suspend fun updateFlashSaleMultipart(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Part("name") name: RequestBody,
        @Part("price") price: RequestBody,
        @Part("stock") stock: RequestBody,
        @Part("descriptions") descriptions: RequestBody,
        @Part("startAt") startAt: RequestBody,
        @Part("endAt") endAt: RequestBody,
        @Part("isActive") isActive: RequestBody,
        @Part image: MultipartBody.Part? // Key field sesuai backend, biasanya "images"
    ): Response<AdminFlashSaleResponse>

    @DELETE("api/flashsale/{id}")
    suspend fun deleteFlashSale(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Response<Unit>

    // ---------- BIDs ----------
    @GET("api/bids/me")
    suspend fun getMyBids(
        @Header("Authorization") token: String
    ): List<BidsResponse>

    @POST("api/bids")
    suspend fun placeBid(
        @Header("Authorization") token: String,
        @Body body: BidsRequest
    ): BidsResponse

    // ADMIN: Get All Bids
    @GET("api/bids")
    suspend fun getAllBids(
        @Header("Authorization") token: String
    ): Response<List<BidsResponse>>

    // ADMIN: Select Winner
    @POST("api/bids/{id}/select-winner")
    suspend fun selectWinner(
        @Header("Authorization") token: String,
        @Path("id") bidId: Int
    ): Response<BidsResponse>

    // ADMIN: Delete Bid
    @DELETE("api/bids/{id}")
    suspend fun deleteBid(
        @Header("Authorization") token: String,
        @Path("id") bidId: Int
    ): Response<Unit>


    @Multipart
    @POST("api/payment")
    suspend fun processPayment(
        @Header("Authorization") token: String,
        @Part("sourceType") sourceType: RequestBody,
        @Part("sourceId") sourceId: RequestBody,
        @Part images: MultipartBody.Part
    ): Response<PaymentResponse>


    @GET("api/payment")
    suspend fun getAllPayments(
        @Header("Authorization") token: String
    ): Response<List<PaymentResponse>>

    @POST("api/payment/{id}/validate")
    suspend fun validatePayment(
        @Header("Authorization") token: String,
        @Path("id") paymentId: Int
    ): Response<PaymentValidationResponse>


    @GET("api/payment/invoice/me")
    suspend fun getMyInvoices(
        @Header("Authorization") token: String
    ): Response<List<UserPaymentInvoiceResponse>>

}
