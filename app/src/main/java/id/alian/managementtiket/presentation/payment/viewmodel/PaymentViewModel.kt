package id.alian.managementtiket.presentation.payment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.alian.managementtiket.commons.Resource
import id.alian.managementtiket.data.remote.dto.order.CreateOrderPaymentDto
import id.alian.managementtiket.domain.use_case.payment.add_payment.AddPaymentUseCase
import id.alian.managementtiket.domain.use_case.payment.get_payment.GetPaymentUseCase
import id.alian.managementtiket.presentation.payment.state.PaymentListState
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(
    private val getPaymentUseCase: GetPaymentUseCase,
    private val addPaymentUseCase: AddPaymentUseCase
) : ViewModel() {

    private val _paymentListState = MutableStateFlow<PaymentListState>(PaymentListState.Empty)
    val paymentListState: StateFlow<PaymentListState> = _paymentListState

    private val _addPaymentState = MutableSharedFlow<Resource<CreateOrderPaymentDto>>()
    val addPaymentState: SharedFlow<Resource<CreateOrderPaymentDto>> = _addPaymentState

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

    fun addPayment(orderId: Int, bankId: String, userBankCode: String) {
        addPaymentUseCase(
            orderId = orderId, bankId = bankId, userBankCode = userBankCode
        ).onEach { result ->
            _addPaymentState.emit(result)
        }.launchIn(viewModelScope)
    }
}