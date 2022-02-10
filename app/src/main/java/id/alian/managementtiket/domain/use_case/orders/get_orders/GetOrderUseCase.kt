package id.alian.managementtiket.domain.use_case.orders.get_orders

import android.util.Log
import id.alian.managementtiket.commons.Constants.ERROR_MESSAGE
import id.alian.managementtiket.commons.Constants.UNEXPECTED_ERROR_MESSAGE
import id.alian.managementtiket.commons.Resource
import id.alian.managementtiket.data.remote.dto.order.toOrder
import id.alian.managementtiket.data.repository.OrderRepository
import id.alian.managementtiket.domain.model.Order
import id.alian.managementtiket.domain.use_case.preferences.DataStoreUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetOrderUseCase @Inject constructor(
    private val repository: OrderRepository,
    private val dataStoreUseCase: DataStoreUseCase,
) {
    operator fun invoke(): Flow<Resource<List<Order>>> = flow {
        try {
            emit(Resource.Loading<List<Order>>())
            dataStoreUseCase.getToken()?.let {
                val orders = repository.getOrders(it).map { it.toOrder() }
                emit(Resource.Success<List<Order>>(orders))
            }
        } catch (e: HttpException) {
            emit(
                Resource.Error<List<Order>>(
                    e.localizedMessage ?: UNEXPECTED_ERROR_MESSAGE
                )
            )
        } catch (e: IOException) {
            Log.d("UseCase", "invoke: $e")
            emit(Resource.Error<List<Order>>(ERROR_MESSAGE))
        }
    }
}