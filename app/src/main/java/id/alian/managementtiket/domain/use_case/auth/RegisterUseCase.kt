package id.alian.managementtiket.domain.use_case.auth

import android.util.Log
import id.alian.managementtiket.commons.Resource
import id.alian.managementtiket.commons.validateEmail
import id.alian.managementtiket.commons.validatePasswordLength
import id.alian.managementtiket.data.remote.dto.auth.RegisterDto
import id.alian.managementtiket.domain.model.User
import id.alian.managementtiket.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(user: User): Flow<Resource<RegisterDto>> = flow {
        if (user.name?.isNotEmpty()!!) {
            if (user.email?.isNotEmpty()!!) {
                if (user.password?.isNotEmpty()!!) {
                    if (validateEmail(user.email)) {
                        if (validatePasswordLength(user.password.length)) {
                            try {
                                emit(Resource.Loading())
                                val login = repository.register(user)
                                emit(Resource.Success<RegisterDto>(login))
                            } catch (e: HttpException) {
                                if (e.response() != null) {
                                    if (e.response()?.code()!! in 401..499) {
                                        emit(Resource.Error("Login gagal, cek kembali email atau password"))
                                    } else {
                                        emit(Resource.Error("Kesalahan Server"))
                                    }
                                } else {
                                    emit(
                                        Resource.Error<RegisterDto>(
                                            e.localizedMessage ?: "an unexpected error occurred"
                                        )
                                    )
                                }
                            } catch (e: IOException) {
                                Log.d("UseCase", "invoke: $e")
                                emit(Resource.Error<RegisterDto>("Couldn't reach server. Check your internet connection"))
                            }
                        } else {
                            emit(Resource.Error<RegisterDto>("Password minimal 8 karakter"))
                        }
                    } else {
                        emit(Resource.Error<RegisterDto>("Format email tidak valid"))
                    }
                } else {
                    emit(Resource.Error<RegisterDto>("Password tidak boleh kosong"))
                }
            } else {
                emit(Resource.Error<RegisterDto>("Email tidak boleh kosong"))
            }
        } else {
            emit(Resource.Error<RegisterDto>("Nama tidak boleh kosong"))
        }
    }
}