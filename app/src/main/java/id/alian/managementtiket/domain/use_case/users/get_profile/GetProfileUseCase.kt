package id.alian.managementtiket.domain.use_case.users.get_profile

import android.util.Log
import id.alian.managementtiket.commons.Resource
import id.alian.managementtiket.domain.model.User
import id.alian.managementtiket.data.repository.UserRepository
import id.alian.managementtiket.domain.use_case.preferences.DataStoreUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetProfileUseCase @Inject constructor(
    private val repository: UserRepository,
    private val dataStoreUseCase: DataStoreUseCase
) {

    operator fun invoke(): Flow<Resource<User>> = flow {
        try {
            emit(Resource.Loading<User>())
            dataStoreUseCase.getToken()?.let {
                val user = repository.getProfile(it).toUser()
                emit(Resource.Success<User>(user))
            }
        } catch (e: HttpException) {
            emit(
                Resource.Error<User>(
                    e.localizedMessage ?: "an unexpected error occurred"
                )
            )
        } catch (e: IOException) {
            Log.d("UseCase", "invoke: $e")
            emit(Resource.Error<User>("Could'n reach server. Check your internet connection"))
        }
    }
}