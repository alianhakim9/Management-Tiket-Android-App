package id.alian.managementtiket.presentation.orders.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.alian.managementtiket.commons.Resource
import id.alian.managementtiket.domain.use_case.orders.get_orders.GetOrderUseCase
import id.alian.managementtiket.presentation.orders.state.OrderListState
import id.alian.managementtiket.presentation.tickets.state.TicketListState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    val getOrderUseCase: GetOrderUseCase
) : ViewModel() {
    private val _orderListState = MutableStateFlow<OrderListState>(OrderListState.Empty)
    val orderListState: StateFlow<OrderListState> = _orderListState

    init {
        getOrders()
    }

    private fun getOrders() {
        getOrderUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    if (result.data != null) {
                        _orderListState.value = OrderListState.Success(result.data)
                    }
                }

                is Resource.Error -> {
                    _orderListState.value =
                        OrderListState.Error(result.message ?: "an expected error occurred")
                }

                is Resource.Loading -> {
                    _orderListState.value = OrderListState.Loading
                }
            }
        }.launchIn(viewModelScope)
    }

}