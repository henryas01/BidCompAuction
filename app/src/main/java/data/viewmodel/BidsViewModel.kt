package data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.bidcompauction.data.model.BidsResponse
import com.example.bidcompauction.network.RetrofitClient
import com.example.bidcompauction.utils.AuthManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.example.bidcompauction.data.model.BidsRequest

class BidsViewModel(application: Application) : AndroidViewModel(application) {
    private val authManager = AuthManager(application)

    // State untuk list bid milik user (GET /api/bids/me)
    private val _myBids = MutableStateFlow<List<BidsResponse>>(emptyList())
    val myBids: StateFlow<List<BidsResponse>> = _myBids.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()


    private val _allBids = MutableStateFlow<List<BidsResponse>>(emptyList())
    val allBids: StateFlow<List<BidsResponse>> = _allBids.asStateFlow()



    init {
        fetchMyBids()
    }

    private fun getAuthHeader(): String = "Bearer ${authManager.getToken()}"

    /**
     * Mengambil data bid milik user dari API
     */
    fun fetchMyBids() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                // Pastikan di ApiService kamu sudah ada:
                // suspend fun getMyBids(@Header("Authorization") token: String): List<BidsResponse>
                val response = RetrofitClient.api.getMyBids(getAuthHeader())
                _myBids.value = response
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    /**
     * Mengirim bid baru ke API
     */
    fun placeBid(flashSaleId: Int, bidPrice: Long, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                val request = BidsRequest(flashSaleId, bidPrice)
                // Pastikan di ApiService kamu sudah ada:
                // suspend fun placeBid(@Header("Authorization") token: String, @Body body: BidsRequest): BidsResponse
                RetrofitClient.api.placeBid(getAuthHeader(), request)

                // Refresh data setelah berhasil ngebid
                fetchMyBids()
                onSuccess()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // ADMIN: Ambil semua data bid
    fun fetchAllBids() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = RetrofitClient.api.getAllBids(getAuthHeader())
                if (response.isSuccessful) {
                    _allBids.value = response.body() ?: emptyList()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    // ADMIN: Pilih Pemenang
    fun selectWinner(bidId: Int, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.api.selectWinner(getAuthHeader(), bidId)
                if (response.isSuccessful) {
                    fetchAllBids() // Refresh data
                    onSuccess()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // ADMIN: Hapus Bid
    fun deleteBid(bidId: Int) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.api.deleteBid(getAuthHeader(), bidId)
                if (response.isSuccessful) fetchAllBids()
            } catch (e: Exception) { e.printStackTrace() }
        }
    }

}