package id.alian.managementtiket.data.repository

import id.alian.managementtiket.data.remote.dto.order.OrdersDto
import id.alian.managementtiket.data.remote.dto.order.CreateOrderPaymentDto
import id.alian.managementtiket.data.remote.dto.order.OrderDetailDto

interface OrderRepository {
    suspend fun getOrders(token: String): List<OrdersDto>
    suspend fun createOrder(
        token: String,
        ticketId: Int,
        ticketCount: Int,
        price: Int
    ): CreateOrderPaymentDto

    suspend fun orderDetail(token: String, orderId: Int): OrderDetailDto
}