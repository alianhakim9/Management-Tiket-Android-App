package id.alian.managementtiket.domain.repository

import id.alian.managementtiket.data.remote.TicketApi
import id.alian.managementtiket.data.remote.dto.order.CreateOrderPaymentDto
import id.alian.managementtiket.data.remote.dto.PaymentDto
import id.alian.managementtiket.data.repository.PaymentRepository
import javax.inject.Inject

class PaymentRepositoryImpl @Inject constructor(
    private val api: TicketApi
) : PaymentRepository {
    override suspend fun getPaymentCheckout(): List<PaymentDto> {
        return api.getPaymentCheckout()
    }

    override suspend fun addPayment(
        token: String,
        orderId: Int,
        sumPrice: Int,
        bankId: String,
        userBankCode: String
    ): CreateOrderPaymentDto {
        return api.addPayment(
            token = "Bearer $token",
            orderId = orderId,
            sumPrice = sumPrice,
            bankId = bankId,
            userBankCode = userBankCode
        )
    }
}