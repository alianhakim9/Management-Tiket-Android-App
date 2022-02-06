package id.alian.managementtiket.domain.use_case.payment.get_payment

import android.util.Log
import id.alian.managementtiket.commons.Resource
import id.alian.managementtiket.data.remote.dto.toPayment
import id.alian.managementtiket.domain.repository.PaymentRepositoryImpl
import id.alian.managementtiket.domain.model.Payment
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetPaymentUseCase @Inject constructor(
    private val repositoryImpl: PaymentRepositoryImpl
) {
    operator fun invoke(): Flow<Resource<List<Payment>>> = flow {
        try {
            emit(Resource.Loading<List<Payment>>())
            val payment = repositoryImpl.getPaymentCheckout().map { it.toPayment() }
            emit(Resource.Success<List<Payment>>(payment))
        } catch (e: HttpException) {
            emit(
                Resource.Error<List<Payment>>(
                    e.localizedMessage ?: "an unexpected error occurred"
                )
            )
        } catch (e: IOException) {
            Log.d("UseCase", "invoke: $e")
            emit(Resource.Error<List<Payment>>("Could'n reach server. Check your internet connection"))
        }
    }
}