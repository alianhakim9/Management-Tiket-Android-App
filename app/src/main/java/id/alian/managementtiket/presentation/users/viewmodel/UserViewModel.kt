package id.alian.managementtiket.presentation.users.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.alian.managementtiket.commons.Resource
import id.alian.managementtiket.domain.use_case.users.get_users.GetUsersUseCase
import id.alian.managementtiket.presentation.users.state.UserListState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    val getUsersUseCase: GetUsersUseCase
) : ViewModel() {

    private val _userListState = MutableStateFlow<UserListState>(UserListState.Empty)
    val userListState: StateFlow<UserListState> = _userListState

    init {
        getUsers()
    }

    private fun getUsers() {
        getUsersUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    if (result.data != null) {
                        _userListState.value = UserListState.Success(result.data)
                    }
                }

                is Resource.Error -> {
                    _userListState.value =
                        UserListState.Error(result.message ?: "an expected error occurred")
                }

                is Resource.Loading -> {
                    _userListState.value = UserListState.Loading
                }
            }
        }.launchIn(viewModelScope)
    }

}