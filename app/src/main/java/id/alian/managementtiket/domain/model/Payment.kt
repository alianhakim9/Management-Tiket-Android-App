package id.alian.managementtiket.domain.model

data class Payment(
    val id: Int,
    val order_id: Int,
    val status: String,
    val sum_price: Int,
)
