package id.alian.managementtiket.data.repository

import id.alian.managementtiket.data.remote.TicketApi
import id.alian.managementtiket.data.remote.dto.UserDto
import id.alian.managementtiket.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val api: TicketApi
) : UserRepository {
    override suspend fun getUsers(): List<UserDto> {
        return api.getUsers()
    }
}