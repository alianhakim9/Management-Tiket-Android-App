package id.alian.managementtiket.data.repository

import id.alian.managementtiket.data.remote.dto.PaymentDto
import id.alian.managementtiket.data.remote.dto.order.CreateOrderPaymentDto

interface PaymentRepository {
    suspend fun getPaymentCheckout(): List<PaymentDto>

    suspend fun addPayment(
        token: String,
        orderId: Int,
        sumPrice: Int,
        bankId: String,
        userBankCode: String
    ): CreateOrderPaymentDto
}