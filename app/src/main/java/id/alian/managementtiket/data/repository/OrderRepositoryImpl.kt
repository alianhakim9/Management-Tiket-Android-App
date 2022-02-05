package id.alian.managementtiket.data.repository

import id.alian.managementtiket.data.remote.TicketApi
import id.alian.managementtiket.data.remote.dto.OrdersDto
import id.alian.managementtiket.data.remote.dto.auth.CreateOrderDto
import id.alian.managementtiket.domain.repository.OrderRepository
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val api: TicketApi
) : OrderRepository {
    override suspend fun getOrders(token: String): List<OrdersDto> {
        return api.getOrders("Bearer $token")
    }

    override suspend fun createOrder(
        token: String,
        ticketId: Int,
        ticketCount: Int,
        price: Int
    ): CreateOrderDto {
        return api.createTicketOrder(
            token = "Bearer $token",
            ticketId, ticketCount, price
        )
    }
}