package id.alian.managementtiket.domain.use_case.orders.get_orders

import android.util.Log
import id.alian.managementtiket.commons.Resource
import id.alian.managementtiket.data.remote.dto.toOrder
import id.alian.managementtiket.domain.model.Order
import id.alian.managementtiket.domain.repository.OrderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetOrderUseCase @Inject constructor(
    val repository: OrderRepository
) {
    operator fun invoke(): Flow<Resource<List<Order>>> = flow {
        try {
            emit(Resource.Loading<List<Order>>())
            val orders = repository.getOrders().map { it.toOrder() }
            emit(Resource.Success<List<Order>>(orders))
        } catch (e: HttpException) {
            emit(
                Resource.Error<List<Order>>(
                    e.localizedMessage ?: "an unexpected error occurred"
                )
            )
        } catch (e: IOException) {
            Log.d("UseCase", "invoke: $e")
            emit(Resource.Error<List<Order>>("Could'n reach server. Check your internet connection"))
        }
    }
}