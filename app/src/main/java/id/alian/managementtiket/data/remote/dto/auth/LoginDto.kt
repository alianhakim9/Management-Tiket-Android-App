package id.alian.managementtiket.data.remote.dto.auth

data class LoginDto(
    val access_token: String,
    val message: String,
    val token_type: String
)