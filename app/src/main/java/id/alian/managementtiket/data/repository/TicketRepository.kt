package id.alian.managementtiket.data.repository

import id.alian.managementtiket.data.remote.dto.ticket.TicketsResponse

interface TicketRepository {
    suspend fun getTickets(page: Int): TicketsResponse
    suspend fun searchTicket(from: String = "", to: String = ""): TicketsResponse
    suspend fun checkConnection(): Boolean
}