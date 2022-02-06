package id.alian.managementtiket.data.repository

import id.alian.managementtiket.data.remote.dto.UserDto

interface UserRepository {
    suspend fun getProfile(token: String): UserDto
}