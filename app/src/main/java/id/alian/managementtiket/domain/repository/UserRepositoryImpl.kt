package id.alian.managementtiket.domain.repository

import id.alian.managementtiket.data.remote.TicketApi
import id.alian.managementtiket.data.remote.dto.UserDto
import id.alian.managementtiket.data.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val api: TicketApi
) : UserRepository {
    override suspend fun getProfile(token: String): UserDto {
        return api.getProfile("Bearer $token")
    }
}