package id.alian.managementtiket.domain.use_case.users.get_users

import android.util.Log
import id.alian.managementtiket.commons.Resource
import id.alian.managementtiket.data.remote.dto.toUser
import id.alian.managementtiket.domain.model.User
import id.alian.managementtiket.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(
    private val repository: UserRepository
) {

    operator fun invoke(): Flow<Resource<List<User>>> = flow {
        try {
            emit(Resource.Loading<List<User>>())
            val users = repository.getUsers().map { it.toUser() }
            emit(Resource.Success<List<User>>(users))
        } catch (e: HttpException) {
            emit(
                Resource.Error<List<User>>(
                    e.localizedMessage ?: "an unexpected error occurred"
                )
            )
        } catch (e: IOException) {
            Log.d("UseCase", "invoke: $e")
            emit(Resource.Error<List<User>>("Could'n reach server. Check your internet connection"))
        }
    }
}