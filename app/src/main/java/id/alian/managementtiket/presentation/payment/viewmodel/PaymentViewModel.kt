package id.alian.managementtiket.presentation.payment.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.alian.managementtiket.commons.Resource
import id.alian.managementtiket.domain.use_case.payment.get_payment.GetPaymentUseCase
import id.alian.managementtiket.presentation.payment.state.PaymentListState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(
    private val getPaymentUseCase: GetPaymentUseCase
) : ViewModel() {

    private val _paymentListState = MutableStateFlow<PaymentListState>(PaymentListState.Empty)
    val paymentListState: StateFlow<PaymentListState> = _paymentListState

    fun getPaymentCheckout() {
        getPaymentUseCase().onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _paymentListState.value = PaymentListState.Loading
                }

                is Resource.Success -> {
                    if (result.data != null) {
                        _paymentListState.value = PaymentListState.Success(result.data)
                    }
                }

                is Resource.Error -> {
                    _paymentListState.value =
                        PaymentListState.Error(result.message ?: "an expected error occurred")
                }
            }
        }
    }
}