package id.alian.managementtiket.data.repository

import id.alian.managementtiket.data.remote.TicketApi
import id.alian.managementtiket.data.remote.dto.PaymentDto
import id.alian.managementtiket.domain.repository.PaymentRepository
import javax.inject.Inject

class PaymentRepositoryImpl @Inject constructor(
    private val api: TicketApi
) : PaymentRepository {
    override suspend fun getPaymentCheckout(): List<PaymentDto> {
        return api.getPaymentCheckout()
    }
}