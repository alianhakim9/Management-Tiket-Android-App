package id.alian.managementtiket.domain.use_case.auth

import android.content.Context
import android.util.Log
import id.alian.managementtiket.commons.*
import id.alian.managementtiket.commons.Constants.ERROR_EMAIL_EMPTY
import id.alian.managementtiket.commons.Constants.ERROR_EMAIL_VALID
import id.alian.managementtiket.commons.Constants.ERROR_NO_INTERNET_CONNECTION
import id.alian.managementtiket.commons.Constants.ERROR_MESSAGE
import id.alian.managementtiket.commons.Constants.ERROR_PASSWORD_EMPTY
import id.alian.managementtiket.commons.Constants.ERROR_PASSWORD_LENGTH_VALIDATE
import id.alian.managementtiket.commons.Constants.UNEXPECTED_ERROR_MESSAGE
import id.alian.managementtiket.data.remote.dto.auth.RegisterDto
import id.alian.managementtiket.domain.model.User
import id.alian.managementtiket.data.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repository: AuthRepository,
) {
    operator fun invoke(user: User): Flow<Resource<RegisterDto>> = flow {
        if (user.name?.isEmpty()!!) {
            emit(Resource.Error<RegisterDto>("Nama tidak boleh kosong"))
        } else if (user.email?.isEmpty()!!) {
            emit(Resource.Error<RegisterDto>(ERROR_EMAIL_EMPTY))
        } else if (!validateEmail(email = user.email)) {
            emit(Resource.Error<RegisterDto>(ERROR_EMAIL_VALID))
        } else if (user.password?.isEmpty()!!) {
            emit(Resource.Error<RegisterDto>(ERROR_PASSWORD_EMPTY))
        } else if (!validatePasswordLength(password = user.password.length)) {
            emit(Resource.Error<RegisterDto>(ERROR_PASSWORD_LENGTH_VALIDATE))
        } else {
            try {
                emit(Resource.Loading())
                val login = repository.register(user)
                emit(Resource.Success<RegisterDto>(login))
            } catch (e: HttpException) {
                if (e.response() != null) {
                    if (e.response()?.code()!! in 401..499) {
                        emit(Resource.Error("Login gagal, cek kembali email atau password"))
                    } else {
                        emit(Resource.Error(ERROR_MESSAGE))
                    }
                } else {
                    emit(
                        Resource.Error<RegisterDto>(
                            e.localizedMessage ?: UNEXPECTED_ERROR_MESSAGE
                        )
                    )
                }
            } catch (e: IOException) {
                Log.d("UseCase", "invoke: $e")
                emit(Resource.Error<RegisterDto>(ERROR_MESSAGE))
            }
        }

    }
}