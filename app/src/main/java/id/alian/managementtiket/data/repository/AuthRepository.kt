package id.alian.managementtiket.data.repository

import id.alian.managementtiket.data.remote.dto.auth.LoginDto
import id.alian.managementtiket.data.remote.dto.auth.RegisterDto
import id.alian.managementtiket.domain.model.User

interface AuthRepository {
    suspend fun login(email: String, password: String): LoginDto
    suspend fun register(user: User): RegisterDto
}