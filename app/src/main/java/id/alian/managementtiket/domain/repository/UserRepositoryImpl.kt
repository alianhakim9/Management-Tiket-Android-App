package id.alian.managementtiket.domain.repository

import android.content.Context
import id.alian.managementtiket.commons.isNetworkAvailable
import id.alian.managementtiket.data.remote.TicketApi
import id.alian.managementtiket.data.remote.dto.user.UserDto
import id.alian.managementtiket.data.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val api: TicketApi,
    private val context: Context
) : UserRepository {
    override suspend fun getProfile(token: String): UserDto {
        return api.getProfile("Bearer $token")
    }

    override suspend fun checkConnection(): Boolean {
        return context.isNetworkAvailable()
    }
}