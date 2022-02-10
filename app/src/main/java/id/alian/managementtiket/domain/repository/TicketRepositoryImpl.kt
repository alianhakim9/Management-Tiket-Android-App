package id.alian.managementtiket.domain.repository

import android.content.Context
import id.alian.managementtiket.commons.isNetworkAvailable
import id.alian.managementtiket.data.remote.TicketApi
import id.alian.managementtiket.data.remote.dto.ticket.TicketsResponse
import id.alian.managementtiket.data.repository.TicketRepository
import javax.inject.Inject

class TicketRepositoryImpl @Inject constructor(
    private val api: TicketApi,
    private val context: Context
) : TicketRepository {
    override suspend fun getTickets(page: Int): TicketsResponse {
        return api.getTickets(page)
    }

    override suspend fun searchTicket(from: String, to: String): TicketsResponse {
        return api.searchTicket(from, to)
    }

    override suspend fun checkConnection(): Boolean {
        return context.isNetworkAvailable()
    }
}