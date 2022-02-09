package id.alian.managementtiket.data.remote.dto.ticket

import id.alian.managementtiket.domain.model.Ticket

data class TicketsResponse(
    val `data`: List<Ticket>,
    val last_page: Int,
    val total: Int
)
