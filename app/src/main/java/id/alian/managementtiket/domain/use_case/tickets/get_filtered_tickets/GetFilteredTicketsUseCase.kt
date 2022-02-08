package id.alian.managementtiket.domain.use_case.tickets.get_filtered_tickets

import id.alian.managementtiket.commons.Constants
import id.alian.managementtiket.commons.Resource
import id.alian.managementtiket.data.remote.dto.ticket.toTicket
import id.alian.managementtiket.data.repository.TicketRepository
import id.alian.managementtiket.domain.model.Ticket
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetFilteredTicketsUseCase @Inject constructor(
    private val repository: TicketRepository,
) {
    operator fun invoke(from: String = "", to: String = ""): Flow<Resource<List<Ticket>>> = flow {
        try {
            emit(Resource.Loading<List<Ticket>>())
            val tickets = repository.getFilteredTickets(
                from, to
            ).map { it.toTicket() }
            emit(Resource.Success<List<Ticket>>(tickets))
        } catch (e: HttpException) {
            emit(
                Resource.Error<List<Ticket>>(
                    e.localizedMessage ?: Constants.UNEXPECTED_ERROR_MESSAGE
                )
            )
        } catch (e: IOException) {
            emit(Resource.Error<List<Ticket>>(Constants.ERROR_MESSAGE))
        }
    }
}