package id.alian.managementtiket.data.remote.dto.order

data class OrderDetailDto(
    val bank_id: String,
    val code_fixed: String,
    val from: String,
    val id: Int,
    val name: String,
    val sum_price: Int,
    val ticket_count: Int,
    val time: String,
    val to: String,
    val url_barcode: String
)