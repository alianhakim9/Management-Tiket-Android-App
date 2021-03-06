package id.alian.managementtiket.domain.repository

import android.content.Context
import id.alian.managementtiket.commons.isNetworkAvailable
import id.alian.managementtiket.data.remote.TicketApi
import id.alian.managementtiket.data.remote.dto.order.CreateOrderPaymentDto
import id.alian.managementtiket.data.remote.dto.payment.PaymentDto
import id.alian.managementtiket.data.repository.PaymentRepository
import javax.inject.Inject

class PaymentRepositoryImpl @Inject constructor(
    private val api: TicketApi,
    private val context: Context
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

    override suspend fun checkConnection(): Boolean {
        return context.isNetworkAvailable()
    }
}