package id.alian.managementtiket.presentation.orders.state

import id.alian.managementtiket.domain.model.Order

sealed class OrderListState {
    data class Success(val data: List<Order>) : OrderListState()
    data class Error(val message: String) : OrderListState()
    object Loading : OrderListState()
    object Empty : OrderListState()
}