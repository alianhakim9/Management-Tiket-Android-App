package id.alian.managementtiket.data.repository

import id.alian.managementtiket.data.remote.dto.ticket.TicketDto

interface TicketRepository {
    suspend fun getTickets(): List<TicketDto>
    suspend fun getFilteredTickets(from: String, to: String): List<TicketDto>
}