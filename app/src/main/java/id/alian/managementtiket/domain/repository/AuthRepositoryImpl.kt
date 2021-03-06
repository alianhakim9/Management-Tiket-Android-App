package id.alian.managementtiket.domain.repository

import android.content.Context
import id.alian.managementtiket.commons.isNetworkAvailable
import id.alian.managementtiket.data.remote.TicketApi
import id.alian.managementtiket.data.remote.dto.auth.LoginDto
import id.alian.managementtiket.data.remote.dto.auth.RegisterDto
import id.alian.managementtiket.data.repository.AuthRepository
import id.alian.managementtiket.domain.model.User
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: TicketApi,
    private val context: Context
) : AuthRepository {
    override suspend fun login(email: String, password: String): LoginDto {
        return api.login(email, password)
    }

    override suspend fun register(user: User): RegisterDto {
        return api.register(user)
    }

    override suspend fun checkInternet(): Boolean {
        return context.isNetworkAvailable()
    }
}