package id.alian.managementtiket.data.repository

import id.alian.managementtiket.data.remote.dto.user.UserDto

interface UserRepository {
    suspend fun getProfile(token: String): UserDto
    suspend fun checkConnection(): Boolean
}