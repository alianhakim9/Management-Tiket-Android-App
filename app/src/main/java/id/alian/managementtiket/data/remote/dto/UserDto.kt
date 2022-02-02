package id.alian.managementtiket.data.remote.dto

import id.alian.managementtiket.domain.model.User

data class UserDto(
    val created_at: String,
    val email: String,
    val email_verified_at: String,
    val id: Int,
    val name: String,
    val updated_at: String
)

fun UserDto.toUser(): User {
    return User(
        email = email,
        id = id,
        name = name
    )
}