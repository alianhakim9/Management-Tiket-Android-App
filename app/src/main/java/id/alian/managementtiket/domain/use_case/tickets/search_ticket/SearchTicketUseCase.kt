package id.alian.managementtiket.domain.use_case.tickets.search_ticket

import android.content.Context
import android.util.Log
import id.alian.managementtiket.commons.Constants.ERROR_MESSAGE
import id.alian.managementtiket.commons.Constants.ERROR_NO_INTERNET_CONNECTION
import id.alian.managementtiket.commons.Constants.UNEXPECTED_ERROR_MESSAGE
import id.alian.managementtiket.commons.Resource
import id.alian.managementtiket.commons.isNetworkAvailable
import id.alian.managementtiket.data.repository.TicketRepository
import id.alian.managementtiket.domain.model.Ticket
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SearchTicketUseCase @Inject constructor(
    private val repository: TicketRepository,
) {
    operator fun invoke(from: String, to: String): Flow<Resource<List<Ticket>>> = flow {
        try {
            emit(Resource.Loading<List<Ticket>>())
            val tickets = repository.searchTicket(from, to)
            if (tickets.data.isEmpty()) {
                emit(Resource.Error("Data tidak ditemukan"))
            } else {
                emit(Resource.Success<List<Ticket>>(tickets.data))
            }
        } catch (e: HttpException) {
            emit(
                Resource.Error<List<Ticket>>(
                    e.localizedMessage ?: UNEXPECTED_ERROR_MESSAGE
                )
            )
        } catch (e: IOException) {
            Log.d("UseCase", "invoke: $e")
            emit(Resource.Error<List<Ticket>>(ERROR_MESSAGE))
        }
    }
}