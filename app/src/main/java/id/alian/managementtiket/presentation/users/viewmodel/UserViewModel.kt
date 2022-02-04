package id.alian.managementtiket.presentation.users.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.alian.managementtiket.commons.Resource
import id.alian.managementtiket.domain.model.User
import id.alian.managementtiket.domain.use_case.users.get_profile.GetProfileUseCase
import id.alian.managementtiket.domain.use_case.users.get_users.GetUsersUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    val getUsersUseCase: GetUsersUseCase,
    val getProfileUseCase: GetProfileUseCase
) : ViewModel() {

    private val _userListState = MutableSharedFlow<Resource<List<User>>>()
    val userListState: SharedFlow<Resource<List<User>>> = _userListState

    private val _userState = MutableSharedFlow<Resource<User>>()
    val userState: SharedFlow<Resource<User>> = _userState

    init {
        getUsers()
    }

    private fun getUsers() {
        getUsersUseCase().onEach { result ->
            _userListState.emit(result)
        }.launchIn(viewModelScope)
    }

    fun profile() {
        getProfileUseCase().onEach { result ->
            _userState.emit(result)
        }.launchIn(viewModelScope)
    }

}