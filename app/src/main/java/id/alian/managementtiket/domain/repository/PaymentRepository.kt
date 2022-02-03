package id.alian.managementtiket.domain.repository

import id.alian.managementtiket.data.remote.dto.PaymentDto

interface PaymentRepository {
    suspend fun getPaymentCheckout(): List<PaymentDto>
}