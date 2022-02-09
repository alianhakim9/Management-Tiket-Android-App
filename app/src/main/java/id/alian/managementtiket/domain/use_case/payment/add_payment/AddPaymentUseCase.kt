package id.alian.managementtiket.domain.use_case.payment.add_payment

import android.util.Log
import id.alian.managementtiket.commons.Constants
import id.alian.managementtiket.commons.Constants.ERROR_MESSAGE
import id.alian.managementtiket.commons.Constants.UNEXPECTED_ERROR_MESSAGE
import id.alian.managementtiket.commons.Resource
import id.alian.managementtiket.data.remote.dto.order.CreateOrderPaymentDto
import id.alian.managementtiket.data.repository.PaymentRepository
import id.alian.managementtiket.domain.use_case.preferences.DataStoreUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class AddPaymentUseCase @Inject constructor(
    private val repository: PaymentRepository,
    private val dataStoreUseCase: DataStoreUseCase
) {
    operator fun invoke(
        orderId: Int,
        bankId: String,
        userBankCode: String
    ): Flow<Resource<CreateOrderPaymentDto>> =
        flow {
            try {
                emit(Resource.Loading<CreateOrderPaymentDto>())
                dataStoreUseCase.getToken()?.let {
                    val response = repository.addPayment(
                        token = it,
                        orderId = orderId,
                        bankId = bankId,
                        userBankCode = userBankCode, sumPrice = 20000
                    )
                    emit(Resource.Success<CreateOrderPaymentDto>(response))
                }
            } catch (e: HttpException) {
                emit(
                    Resource.Error<CreateOrderPaymentDto>(
                        e.localizedMessage ?: UNEXPECTED_ERROR_MESSAGE
                    )
                )
            } catch (e: IOException) {
                Log.d("UseCase", "invoke: $e")
                emit(Resource.Error<CreateOrderPaymentDto>(ERROR_MESSAGE))
            }
        }
}