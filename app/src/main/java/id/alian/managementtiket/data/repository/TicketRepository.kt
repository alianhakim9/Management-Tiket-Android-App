package id.alian.managementtiket.data.repository

import id.alian.managementtiket.data.remote.dto.ticket.TicketsResponse

interface TicketRepository {
    suspend fun getTickets(page: Int): TicketsResponse
}