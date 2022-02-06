package id.alian.managementtiket.domain.use_case.orders.order_detail

import android.util.Log
import id.alian.managementtiket.commons.Resource
import id.alian.managementtiket.data.remote.dto.order.OrderDetailDto
import id.alian.managementtiket.data.repository.OrderRepository
import id.alian.managementtiket.domain.model.Order
import id.alian.managementtiket.domain.use_case.preferences.DataStoreUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class OrderDetailUseCase @Inject constructor(
    private val repository: OrderRepository,
    private val dataStoreUseCase: DataStoreUseCase
) {
    operator fun invoke(): Flow<Resource<List<OrderDetailDto>>> = flow {
        try {
            emit(Resource.Loading<List<OrderDetailDto>>())
            dataStoreUseCase.getToken()?.let {
                val orderDetail = repository.orderDetail(it)
                emit(Resource.Success(orderDetail))
            }
        } catch (e: HttpException) {
            emit(
                Resource.Error<List<OrderDetailDto>>(
                    e.localizedMessage ?: "an unexpected error occurred"
                )
            )
        } catch (e: IOException) {
            Log.d("UseCase", "invoke: $e")
            emit(Resource.Error<List<OrderDetailDto>>("Could'n reach server. Check your internet connection"))
        }
    }
}