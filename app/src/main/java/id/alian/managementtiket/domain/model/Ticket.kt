package id.alian.managementtiket.domain.model

data class Ticket(
    val from: String,
    val id: Int,
    val price: String,
    val ticket_stock: String,
    val time: String,
    val to: String,
)
