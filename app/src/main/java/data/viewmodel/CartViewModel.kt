package data.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.bidcompauction.userMainActivity.checkout.CartItemUi

class CartViewModel : ViewModel() {

    private val _cartItems = mutableStateListOf<CartItemUi>()
    val cartItems: List<CartItemUi> get() = _cartItems

    fun addToCart(newItem: CartItemUi) {
        val existingIndex = _cartItems.indexOfFirst { it.id == newItem.id }
        if (existingIndex != -1) {
            // Jika sudah ada, update quantity
            val updatedItem = _cartItems[existingIndex].copy(
                qty = _cartItems[existingIndex].qty + newItem.qty
            )
            _cartItems[existingIndex] = updatedItem
        } else {
            _cartItems.add(newItem)
        }
    }

    fun updateQty(id: String, newQty: Int) {
        val index = _cartItems.indexOfFirst { it.id == id }
        if (index != -1 && newQty > 0) {
            _cartItems[index] = _cartItems[index].copy(qty = newQty)
        }
    }

    fun removeItem(id: String) {
        val index = _cartItems.indexOfFirst { it.id == id }
        if (index != -1) {
            _cartItems.removeAt(index)
        }
    }
}