package id.alian.managementtiket.data.remote.dto.order

import id.alian.managementtiket.data.remote.dto.user.UserDto
import id.alian.managementtiket.domain.model.Order
import id.alian.managementtiket.domain.model.Ticket
import id.alian.managementtiket.domain.model.User

data class OrdersDto(
    val id: Int,
    val user_id: Int,
    val ticket_id: Int,
    val ticket_count: Int,
    val price: Int,
    val status: String,
    val created_at: String,
    val updated_at: String,
    val users: UserDto,
    val ticket: Ticket
)

fun OrdersDto.toUser(): User {
    return User(
        email = users.email,
        id = users.id,
        name = users.name
    )
}

fun OrdersDto.toTicket(): Ticket {
    return Ticket(
        from = ticket.from,
        id = ticket.id,
        price = ticket.price,
        ticket_stock = ticket.ticket_stock,
        time = ticket.time,
        to = ticket.to
    )
}

fun OrdersDto.toOrder(): Order {
    return Order(
        id = id,
        user_id = user_id,
        ticket_id = ticket_id,
        ticket_count = ticket_count,
        price = price,
        status = status,
        users = users,
        ticket = ticket
    )
}