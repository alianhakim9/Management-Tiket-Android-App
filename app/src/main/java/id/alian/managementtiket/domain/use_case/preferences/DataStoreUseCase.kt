package id.alian.managementtiket.domain.use_case.preferences

import id.alian.managementtiket.commons.Constants.TOKEN_KEY
import id.alian.managementtiket.commons.Constants.USER_NAME_KEY
import id.alian.managementtiket.data.repository.DataStoreRepository
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class DataStoreUseCase @Inject constructor(
    private val repository: DataStoreRepository
) {
    suspend fun getToken(): String? = runBlocking {
        repository.getString(TOKEN_KEY)
    }

    suspend fun saveToken(value: String) {
        repository.putString(TOKEN_KEY, value)
    }

    suspend fun removeToken(key: String) {
        repository.removeString(key)
    }

    suspend fun saveUserName(value: String) {
        repository.putString(USER_NAME_KEY, value)
    }

    suspend fun removeUserName(key: String) {
        repository.removeString(key)
    }
}