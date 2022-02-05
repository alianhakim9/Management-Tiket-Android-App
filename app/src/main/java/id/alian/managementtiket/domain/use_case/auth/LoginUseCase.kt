package id.alian.managementtiket.domain.use_case.auth

import android.util.Log
import id.alian.managementtiket.commons.Resource
import id.alian.managementtiket.commons.validateEmail
import id.alian.managementtiket.commons.validatePasswordLength
import id.alian.managementtiket.data.remote.dto.auth.LoginDto
import id.alian.managementtiket.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(email: String, password: String): Flow<Resource<LoginDto>> = flow {
        if (email.isNotEmpty()) {
            if (password.isNotEmpty()) {
                if (validateEmail(email)) {
                    if (validatePasswordLength(password.length)) {
                        try {
                            emit(Resource.Loading<LoginDto>())
                            val login = repository.login(email, password)
                            emit(Resource.Success<LoginDto>(login))
                        } catch (e: HttpException) {
                            if (e.response() != null) {
                                if (e.response()?.code()!! in 401..499) {
                                    emit(Resource.Error("Login gagal, cek kembali email atau password"))
                                } else {
                                    emit(Resource.Error("Kesalahan Server"))
                                }
                            } else {
                                emit(
                                    Resource.Error<LoginDto>(
                                        e.localizedMessage ?: "an unexpected error occurred"
                                    )
                                )
                            }
                        } catch (e: IOException) {
                            Log.d("UseCase", "invoke: $e")
                            emit(Resource.Error<LoginDto>("Couldn't reach server. Check your internet connection"))
                        }
                    } else {
                        emit(Resource.Error<LoginDto>("Password minimal 8 karakter"))
                    }
                } else {
                    emit(Resource.Error<LoginDto>("Format email tidak valid"))
                }
            } else {
                emit(Resource.Error<LoginDto>("Password tidak boleh kosong"))
            }
        } else {
            emit(Resource.Error<LoginDto>("Email tidak boleh kosong"))
        }
    }
}