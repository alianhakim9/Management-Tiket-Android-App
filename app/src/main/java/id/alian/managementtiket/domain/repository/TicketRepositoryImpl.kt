package id.alian.managementtiket.domain.repository

import id.alian.managementtiket.data.remote.TicketApi
import id.alian.managementtiket.data.remote.dto.TicketDto
import id.alian.managementtiket.data.repository.TicketRepository
import javax.inject.Inject

class TicketRepositoryImpl @Inject constructor(
    private val api: TicketApi
) : TicketRepository {
    override suspend fun getTickets(): List<TicketDto> {
        return api.getTickets()
    }
}