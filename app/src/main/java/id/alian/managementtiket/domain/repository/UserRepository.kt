package id.alian.managementtiket.domain.repository

import id.alian.managementtiket.data.remote.dto.UserDto

interface UserRepository {
    suspend fun getUsers(): List<UserDto>
}