package id.alian.managementtiket.data.repository

import id.alian.managementtiket.data.remote.dto.TicketDto

interface TicketRepository {
    suspend fun getTickets(): List<TicketDto>
}