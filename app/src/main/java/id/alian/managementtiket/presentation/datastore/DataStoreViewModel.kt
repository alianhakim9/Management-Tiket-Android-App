package id.alian.managementtiket.presentation.datastore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.alian.managementtiket.commons.Constants.TOKEN_KEY
import id.alian.managementtiket.domain.use_case.preferences.DataStoreUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DataStoreViewModel @Inject constructor(
    private val dataStoreUseCase: DataStoreUseCase
) : ViewModel() {

    private val _token = MutableLiveData<String>("")
    val token: LiveData<String> = _token

    fun getToken() {
        viewModelScope.launch {
            _token.value = dataStoreUseCase.getToken()
        }
    }

    fun saveToken(value: String) {
        viewModelScope.launch {
            dataStoreUseCase.saveToken(value)
        }
    }

    fun logout() {
        viewModelScope.launch {
            dataStoreUseCase.removeToken(TOKEN_KEY)
        }
    }
}