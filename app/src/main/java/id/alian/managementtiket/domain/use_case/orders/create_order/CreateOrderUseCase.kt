package id.alian.managementtiket.domain.use_case.orders.create_order

import android.util.Log
import id.alian.managementtiket.commons.Resource
import id.alian.managementtiket.data.remote.dto.order.CreateOrderPaymentDto
import id.alian.managementtiket.data.repository.OrderRepository
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
    ): Flow<Resource<CreateOrderPaymentDto>> = flow {
        try {
            emit(Resource.Loading<CreateOrderPaymentDto>())
            dataStoreUseCase.getToken()?.let {
                val order = repository.createOrder(
                    it, ticketId, ticketCount, price
                )
                emit(Resource.Success<CreateOrderPaymentDto>(order))
            }
        } catch (e: HttpException) {
            emit(
                Resource.Error<CreateOrderPaymentDto>(
                    e.localizedMessage ?: "an unexpected error occurred"
                )
            )
        } catch (e: IOException) {
            Log.d("UseCase", "invoke: $e")
            emit(Resource.Error<CreateOrderPaymentDto>("couldn't reach server. Check your internet connection"))
        }
    }
}