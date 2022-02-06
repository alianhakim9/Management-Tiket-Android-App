package id.alian.managementtiket.data.repository

import id.alian.managementtiket.data.remote.dto.order.OrdersDto
import id.alian.managementtiket.data.remote.dto.order.CreateOrderPaymentDto

interface OrderRepository {
    suspend fun getOrders(token: String): List<OrdersDto>
    suspend fun createOrder(
        token: String,
        ticketId: Int,
        ticketCount: Int,
        price: Int
    ): CreateOrderPaymentDto
}