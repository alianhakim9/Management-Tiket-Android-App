package id.alian.managementtiket.domain.use_case.auth

import android.content.Context
import android.util.Log
import id.alian.managementtiket.commons.Constants.ERROR_EMAIL_EMPTY
import id.alian.managementtiket.commons.Constants.ERROR_EMAIL_VALID
import id.alian.managementtiket.commons.Constants.ERROR_MESSAGE
import id.alian.managementtiket.commons.Constants.ERROR_NO_INTERNET_CONNECTION
import id.alian.managementtiket.commons.Constants.UNEXPECTED_ERROR_MESSAGE
import id.alian.managementtiket.commons.Resource
import id.alian.managementtiket.commons.isNetworkAvailable
import id.alian.managementtiket.commons.validateEmail
import id.alian.managementtiket.data.remote.dto.auth.LoginDto
import id.alian.managementtiket.data.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthRepository,
) {
    operator fun invoke(email: String, password: String): Flow<Resource<LoginDto>> = flow {
        if (email.isEmpty()) {
            emit(Resource.Error<LoginDto>(ERROR_EMAIL_EMPTY))
        } else if (!validateEmail(email)) {
            emit(Resource.Error<LoginDto>(ERROR_EMAIL_VALID))
        } else {
            try {
                emit(Resource.Loading<LoginDto>())
                val login = repository.login(email, password)
                emit(Resource.Success<LoginDto>(login))
            } catch (e: HttpException) {
                if (e.response() != null) {
                    if (e.response()?.code()!! in 401..499) {
                        emit(Resource.Error("Login gagal, cek kembali email atau password"))
                    } else {
                        emit(Resource.Error(ERROR_MESSAGE))
                    }
                } else {
                    emit(
                        Resource.Error<LoginDto>(
                            e.localizedMessage ?: UNEXPECTED_ERROR_MESSAGE
                        )
                    )
                }
            } catch (e: IOException) {
                Log.d("UseCase", "invoke: $e")
                emit(Resource.Error<LoginDto>(ERROR_MESSAGE))
            }
        }
    }
}