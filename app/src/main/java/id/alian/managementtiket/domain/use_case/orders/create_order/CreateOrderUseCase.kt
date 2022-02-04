package id.alian.managementtiket.domain.use_case.orders.create_order

import android.util.Log
import id.alian.managementtiket.commons.Resource
import id.alian.managementtiket.data.remote.dto.auth.CreateOrderDto
import id.alian.managementtiket.domain.repository.OrderRepository
import id.alian.managementtiket.domain.use_case.preferences.DataStoreUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CreateOrderUseCase @Inject constructor(
    private val repository: OrderRepository,
    private val dataStoreUseCase: DataStoreUseCase
) {
    operator fun invoke(
        ticketId: Int,
        ticketCount: Int,
        price: Int
    ): Flow<Resource<CreateOrderDto>> = flow {
        try {
            emit(Resource.Loading<CreateOrderDto>())
            dataStoreUseCase.getToken()?.let {
                val order = repository.createOrder(
                    it, ticketId, ticketCount, price
                )
                emit(Resource.Success<CreateOrderDto>(order))
            }
        } catch (e: HttpException) {
            emit(
                Resource.Error<CreateOrderDto>(
                    e.localizedMessage ?: "an unexpected error occurred"
                )
            )
        } catch (e: IOException) {
            Log.d("UseCase", "invoke: $e")
            emit(Resource.Error<CreateOrderDto>("couldn't reach server. Check your internet connection"))
        }
    }
}