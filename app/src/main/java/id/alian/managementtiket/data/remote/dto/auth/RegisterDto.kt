package id.alian.managementtiket.data.remote.dto.auth

data class RegisterDto(
    val access_token: String,
    val `data`: Data,
    val token_type: String
)