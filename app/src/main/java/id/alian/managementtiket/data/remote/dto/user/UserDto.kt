package id.alian.managementtiket.data.remote.dto.user

import id.alian.managementtiket.domain.model.User
import java.io.Serializable

data class UserDto(
    val created_at: String,
    val email: String,
    val email_verified_at: String,
    val id: Int,
    val name: String,
    val updated_at: String
) : Serializable

fun UserDto.toUser(): User {
    return User(
        email = email,
        id = id,
        name = name
    )
}