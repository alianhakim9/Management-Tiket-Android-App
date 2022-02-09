package id.alian.managementtiket.data.remote

import id.alian.managementtiket.data.remote.dto.auth.LoginDto
import id.alian.managementtiket.data.remote.dto.auth.RegisterDto
import id.alian.managementtiket.data.remote.dto.order.CreateOrderPaymentDto
import id.alian.managementtiket.data.remote.dto.order.OrderDetailDto
import id.alian.managementtiket.data.remote.dto.order.OrdersDto
import id.alian.managementtiket.data.remote.dto.payment.PaymentDto
import id.alian.managementtiket.data.remote.dto.ticket.TicketsResponse
import id.alian.managementtiket.data.remote.dto.user.UserDto
import id.alian.managementtiket.domain.model.User
import retrofit2.http.*

interface TicketApi {

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
    suspend fun getTickets(
        @Query("page") page: Int
    ): TicketsResponse

    @GET("ticket/list-get")
    suspend fun searchTicket(
        @Query("from") from: String,
        @Query("to") to: String
    ): TicketsResponse

    // order
    @FormUrlEncoded
    @POST("auth/user/order-ticket/create")
    suspend fun createTicketOrder(
        @Header("Authorization") token: String,
        @Field("ticket_id") ticketId: Int,
        @Field("ticket_count") ticketCount: Int,
        @Field("price") price: Int
    ): CreateOrderPaymentDto

    @GET("auth/user/order/list-order")
    suspend fun getOrders(
        @Header("Authorization") token: String
    ): List<OrdersDto>

    @FormUrlEncoded
    @POST("auth/user/checkout-order")
    suspend fun addPayment(
        @Header("Authorization") token: String,
        @Field("order_id") orderId: Int,
        @Field("sum_price") sumPrice: Int = 200000,
        @Field("bank_id") bankId: String,
        @Field("code_bank_user") userBankCode: String
    ): CreateOrderPaymentDto

    @GET("auth/user/status-payment/{orderId}")
    suspend fun orderDetail(
        @Header("Authorization") token: String,
        @Path("orderId") orderId: Int
    ): OrderDetailDto

}