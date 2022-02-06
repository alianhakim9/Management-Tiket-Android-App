package id.alian.managementtiket.data.remote.dto.ticket

import id.alian.managementtiket.domain.model.Ticket
import java.io.Serializable

data class TicketDto(
    val created_at: String,
    val from: String,
    val id: Int,
    val price: String,
    val ticket_stock: String,
    val time: String,
    val to: String,
    val updated_at: String
) : Serializable

fun TicketDto.toTicket(): Ticket {
    return Ticket(
        from = from,
        id = id,
        price = price,
        ticket_stock = ticket_stock,
        time = time,
        to = to
    )
}

