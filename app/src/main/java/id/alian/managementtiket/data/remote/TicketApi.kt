package id.alian.managementtiket.data.remote

import id.alian.managementtiket.data.remote.dto.OrdersDto
import id.alian.managementtiket.data.remote.dto.PaymentDto
import id.alian.managementtiket.data.remote.dto.TicketDto
import id.alian.managementtiket.data.remote.dto.UserDto
import id.alian.managementtiket.data.remote.dto.auth.CreateOrderDto
import id.alian.managementtiket.data.remote.dto.auth.LoginDto
import id.alian.managementtiket.data.remote.dto.auth.RegisterDto
import id.alian.managementtiket.domain.model.User
import retrofit2.http.*

interface TicketApi {
    @GET("auth/user/get")
    suspend fun getUsers(): List<UserDto>

    @GET("order/list-order")
    suspend fun getOrders(): List<OrdersDto>

    @GET("payment/payment-checkout")
    suspend fun getPaymentCheckout(): List<PaymentDto>

    // user
    @POST("auth/user/register")
    suspend fun register(
        @Body user: User
    ): RegisterDto

    @FormUrlEncoded
    @POST("auth/user/login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginDto

    @GET("auth/user/profile")
    suspend fun getProfile(
        @Header("Authorization") token: String
    ): UserDto

    // ticket
    @GET("ticket/list-get")
    suspend fun getTickets(): List<TicketDto>

    @FormUrlEncoded
    @POST("auth/user/order-ticket/create")
    suspend fun createTicketOrder(
        @Header("Authorization") token: String,
        @Field("ticket_id") ticketId: Int,
        @Field("ticket_count") ticketCount: Int,
        @Field("price") price: Int
    ): CreateOrderDto
}