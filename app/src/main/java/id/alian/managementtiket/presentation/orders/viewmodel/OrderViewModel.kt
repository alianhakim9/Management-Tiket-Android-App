package id.alian.managementtiket.presentation.orders.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.alian.managementtiket.commons.Resource
import id.alian.managementtiket.data.remote.dto.order.CreateOrderPaymentDto
import id.alian.managementtiket.data.remote.dto.order.OrderDetailDto
import id.alian.managementtiket.domain.model.Order
import id.alian.managementtiket.domain.use_case.orders.create_order.CreateOrderUseCase
import id.alian.managementtiket.domain.use_case.orders.get_orders.GetOrderUseCase
import id.alian.managementtiket.domain.use_case.orders.order_detail.OrderDetailUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    val getOrderUseCase: GetOrderUseCase,
    val createOrderUseCase: CreateOrderUseCase,
    val orderDetailUseCase: OrderDetailUseCase
) : ViewModel() {

    private val _orderListState = MutableSharedFlow<Resource<List<Order>>>()
    val orderListState: SharedFlow<Resource<List<Order>>> = _orderListState

    private val _createOrderState = MutableSharedFlow<Resource<CreateOrderPaymentDto>>()
    val createOrderPaymentState: SharedFlow<Resource<CreateOrderPaymentDto>> = _createOrderState

    private val _orderDetailState = MutableSharedFlow<Resource<OrderDetailDto>>()
    val orderDetailState: SharedFlow<Resource<OrderDetailDto>> = _orderDetailState

    private var _ticketCount = MutableLiveData<Int>(0)
    val ticketCount: LiveData<Int> = _ticketCount

    init {
        getOrders()
    }

    fun getOrders() {
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

    fun orderDetail(orderId: Int) {
        orderDetailUseCase(orderId).onEach { result ->
            _orderDetailState.emit(result)
        }.launchIn(viewModelScope)
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