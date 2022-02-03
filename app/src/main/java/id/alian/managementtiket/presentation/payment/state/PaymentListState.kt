package id.alian.managementtiket.presentation.payment.state

import id.alian.managementtiket.domain.model.Payment

sealed class PaymentListState {
    data class Success(val data: List<Payment>) : PaymentListState()
    data class Error(val message: String) : PaymentListState()
    object Loading : PaymentListState()
    object Empty : PaymentListState()
}