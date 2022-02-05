package id.alian.managementtiket.domain.repository

import id.alian.managementtiket.data.remote.dto.OrdersDto
import id.alian.managementtiket.data.remote.dto.auth.CreateOrderDto

interface OrderRepository {
    suspend fun getOrders(token: String): List<OrdersDto>
    suspend fun createOrder(
        token: String,
        ticketId: Int,
        ticketCount: Int,
        price: Int
    ): CreateOrderDto
}