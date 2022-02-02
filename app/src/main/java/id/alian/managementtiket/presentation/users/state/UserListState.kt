package id.alian.managementtiket.presentation.users.state

import id.alian.managementtiket.domain.model.User

sealed class UserListState {
    data class Success(val data: List<User>) : UserListState()
    data class Error(val message: String) : UserListState()
    object Loading : UserListState()
    object Empty : UserListState()
}