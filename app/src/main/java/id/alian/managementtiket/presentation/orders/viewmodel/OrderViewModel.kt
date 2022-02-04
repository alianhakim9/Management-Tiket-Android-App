package id.alian.managementtiket.presentation.orders.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.alian.managementtiket.commons.Resource
import id.alian.managementtiket.data.remote.dto.auth.CreateOrderDto
import id.alian.managementtiket.domain.model.Order
import id.alian.managementtiket.domain.use_case.orders.create_order.CreateOrderUseCase
import id.alian.managementtiket.domain.use_case.orders.get_orders.GetOrderUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    val getOrderUseCase: GetOrderUseCase,
    val createOrderUseCase: CreateOrderUseCase,
) : ViewModel() {

    private val _orderListState = MutableSharedFlow<Resource<List<Order>>>()
    val orderListState: SharedFlow<Resource<List<Order>>> = _orderListState

    private val _createOrderState = MutableSharedFlow<Resource<CreateOrderDto>>()
    val createOrderState: SharedFlow<Resource<CreateOrderDto>> = _createOrderState

    private var _ticketCount = MutableLiveData<Int>(0)
    val ticketCount: LiveData<Int> = _ticketCount

    init {
        getOrders()
    }

    private fun getOrders() {
        getOrderUseCase().onEach { result ->
            _orderListState.emit(result)
        }.launchIn(viewModelScope)
    }

    fun createOrder(ticketId: Int, ticketCount: Int, price: Int) {
        if (ticketCount > 0) {
            createOrderUseCase(
                ticketId, ticketCount, price
            ).onEach { result ->
                _createOrderState.emit(result)
            }.launchIn(viewModelScope)
        }
    }

    fun decreaseCount() {
        if (_ticketCount.value != 0) {
            _ticketCount.value = _ticketCount.value?.minus(1)
        }
    }

    fun increaseCount() {
        _ticketCount.value = _ticketCount.value?.plus(1)
    }

}