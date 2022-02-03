package id.alian.managementtiket.data.remote

import id.alian.managementtiket.data.remote.dto.OrdersDto
import id.alian.managementtiket.data.remote.dto.PaymentDto
import id.alian.managementtiket.data.remote.dto.TicketDto
import id.alian.managementtiket.data.remote.dto.UserDto
import retrofit2.http.GET

interface TicketApi {
    @GET("auth/user/get")
    suspend fun getUsers(): List<UserDto>

    @GET("ticket/list-get")
    suspend fun getTickets(): List<TicketDto>

    @GET("order/list-order")
    suspend fun getOrders(): List<OrdersDto>

    @GET("api/payment/payment-checkout")
    suspend fun getPaymentCheckout(): List<PaymentDto>
}