package id.alian.managementtiket.presentation.auth.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import id.alian.managementtiket.commons.Constants
import id.alian.managementtiket.commons.Constants.ERROR_NO_INTERNET_CONNECTION
import id.alian.managementtiket.commons.Resource
import id.alian.managementtiket.commons.isNetworkAvailable
import id.alian.managementtiket.data.remote.dto.auth.LoginDto
import id.alian.managementtiket.data.remote.dto.auth.RegisterDto
import id.alian.managementtiket.domain.model.User
import id.alian.managementtiket.domain.use_case.auth.LoginUseCase
import id.alian.managementtiket.domain.use_case.auth.RegisterUseCase
import id.alian.managementtiket.domain.use_case.preferences.DataStoreUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase,
    private val dataStoreUseCase: DataStoreUseCase,
) : ViewModel() {

    private val _login = MutableSharedFlow<Resource<LoginDto>>()
    val login: SharedFlow<Resource<LoginDto>> = _login

    private val _registerState = MutableSharedFlow<Resource<RegisterDto>>()
    val registerState: SharedFlow<Resource<RegisterDto>> = _registerState

    private val _noInternet = MutableSharedFlow<String>()
    val noInternet: SharedFlow<String> = _noInternet

    fun login(email: String, password: String) {
        loginUseCase(email, password).onEach { result ->
            _login.emit(result)
            when (result) {
                is Resource.Success -> {
                    if (result.data != null) {
                        _login.emit(Resource.Success(result.data))
                        dataStoreUseCase.saveToken(result.data.access_token)
                    }
                }
                else -> Unit
            }
        }.launchIn(viewModelScope)
        viewModelScope.launch {
            _noInternet.emit(ERROR_NO_INTERNET_CONNECTION)
        }
    }

    fun register(user: User) {
        registerUseCase(user).onEach { result ->
            _registerState.emit(result)
            when (result) {
                is Resource.Success -> {
                    if (result.data != null) {
                        _registerState.emit(Resource.Success(result.data))
                        dataStoreUseCase.saveToken(result.data.access_token)
                    }
                }
                else -> Unit
            }
        }.launchIn(viewModelScope)
        viewModelScope.launch {
            _noInternet.emit(ERROR_NO_INTERNET_CONNECTION)
        }
    }
}