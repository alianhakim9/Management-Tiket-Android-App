package id.alian.managementtiket.domain.repository

import android.content.Context
import id.alian.managementtiket.commons.isNetworkAvailable
import id.alian.managementtiket.data.remote.TicketApi
import id.alian.managementtiket.data.remote.dto.order.OrdersDto
import id.alian.managementtiket.data.remote.dto.order.CreateOrderPaymentDto
import id.alian.managementtiket.data.remote.dto.order.OrderDetailDto
import id.alian.managementtiket.data.repository.OrderRepository
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val api: TicketApi,
    private val context: Context
) : OrderRepository {
    override suspend fun getOrders(token: String): List<OrdersDto> {
        return api.getOrders("Bearer $token")
    }

    override suspend fun createOrder(
        token: String,
        ticketId: Int,
        ticketCount: Int,
        price: Int
    ): CreateOrderPaymentDto {
        return api.createTicketOrder(
            token = "Bearer $token",
            ticketId, ticketCount, price
        )
    }

    override suspend fun orderDetail(token: String, orderId: Int): OrderDetailDto {
        return api.orderDetail("Bearer $token", orderId)
    }

    override suspend fun checkConnection(): Boolean {
        return context.isNetworkAvailable()
    }

}