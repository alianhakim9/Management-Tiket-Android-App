package id.alian.managementtiket.data.remote.dto

import id.alian.managementtiket.domain.model.Payment

data class PaymentDto(
    val created_at: String,
    val id: Int,
    val order_id: Int,
    val status: String,
    val sum_price: Int,
    val updated_at: String
)

fun PaymentDto.toPayment(): Payment {
    return Payment(
        id = id,
        order_id = order_id,
        status = status,
        sum_price = sum_price
    )
}