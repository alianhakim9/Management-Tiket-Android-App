package id.alian.managementtiket.presentation.users.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.alian.managementtiket.commons.Resource
import id.alian.managementtiket.domain.model.User
import id.alian.managementtiket.domain.use_case.users.get_profile.GetProfileUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    val getProfileUseCase: GetProfileUseCase
) : ViewModel() {

    private val _userState = MutableSharedFlow<Resource<User>>()
    val userState: SharedFlow<Resource<User>> = _userState

    fun profile() {
        getProfileUseCase().onEach { result ->
            _userState.emit(result)
        }.launchIn(viewModelScope)
    }
}