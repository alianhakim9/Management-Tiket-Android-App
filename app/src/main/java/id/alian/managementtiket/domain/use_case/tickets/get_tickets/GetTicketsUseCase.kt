package id.alian.managementtiket.domain.use_case.tickets.get_tickets

import android.util.Log
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
            val users = repository.getTickets().map { it.toTicket() }
            emit(Resource.Success<List<Ticket>>(users))
        } catch (e: HttpException) {
            emit(
                Resource.Error<List<Ticket>>(
                    e.localizedMessage ?: "an unexpected error occurred"
                )
            )
        } catch (e: IOException) {
            Log.d("UseCase", "invoke: $e")
            emit(Resource.Error<List<Ticket>>("Could'n reach server. Check your internet connection"))
        }
    }

}