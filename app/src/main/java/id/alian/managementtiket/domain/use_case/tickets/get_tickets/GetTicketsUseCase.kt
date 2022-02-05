package id.alian.managementtiket.domain.use_case.tickets.get_tickets

import id.alian.managementtiket.commons.Constants.ERROR_MESSAGE
import id.alian.managementtiket.commons.Constants.UNEXPECTED_ERROR_MESSAGE
import id.alian.managementtiket.commons.Resource
import id.alian.managementtiket.data.remote.dto.toTicket
import id.alian.managementtiket.domain.model.Ticket
import id.alian.managementtiket.domain.repository.TicketRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetTicketsUseCase @Inject constructor(
    private val repository: TicketRepository
) {
    operator fun invoke(): Flow<Resource<List<Ticket>>> = flow {
        try {
            emit(Resource.Loading<List<Ticket>>())
            val tickets = repository.getTickets().map { it.toTicket() }
            emit(Resource.Success<List<Ticket>>(tickets))
        } catch (e: HttpException) {
            emit(
                Resource.Error<List<Ticket>>(
                    e.localizedMessage ?: UNEXPECTED_ERROR_MESSAGE
                )
            )
        } catch (e: IOException) {
            emit(Resource.Error<List<Ticket>>(ERROR_MESSAGE))
        }
    }
}