package id.alian.managementtiket.presentation.payment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.alian.managementtiket.commons.Resource
import id.alian.managementtiket.data.remote.dto.order.CreateOrderPaymentDto
import id.alian.managementtiket.domain.use_case.payment.add_payment.AddPaymentUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(
    private val addPaymentUseCase: AddPaymentUseCase
) : ViewModel() {

    private val _addPaymentState = MutableSharedFlow<Resource<CreateOrderPaymentDto>>()
    val addPaymentState: SharedFlow<Resource<CreateOrderPaymentDto>> = _addPaymentState

    fun addPayment(orderId: Int, bankId: String, userBankCode: String) {
        addPaymentUseCase(
            orderId = orderId, bankId = bankId, userBankCode = userBankCode
        ).onEach { result ->
            _addPaymentState.emit(result)
        }.launchIn(viewModelScope)
    }
}