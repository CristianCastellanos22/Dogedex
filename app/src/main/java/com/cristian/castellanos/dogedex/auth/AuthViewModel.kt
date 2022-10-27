package com.cristian.castellanos.dogedex.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cristian.castellanos.dogedex.api.ApiResponseStatus
import com.cristian.castellanos.dogedex.model.User
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> get() = _user

    private val _status = MutableLiveData<ApiResponseStatus<User>>()
    val status: LiveData<ApiResponseStatus<User>> get() = _status

    private val authRepository = AuthRepository()

    fun signUp(email: String, password: String, passwordConfirmation: String) {
        viewModelScope.launch {
            _status.value = ApiResponseStatus.Loading()
            handleResponseStatus(authRepository.signUp(email, password, passwordConfirmation))
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _status.value = ApiResponseStatus.Loading()
            handleResponseStatus(
                authRepository.login(
                    email,
                    password
                )
            )
        }
    }

    private fun handleResponseStatus(apiResponseStatus: ApiResponseStatus<User>) {
        if (apiResponseStatus is ApiResponseStatus.Success) {
            _user.value = apiResponseStatus.data ?: User(0L, "", "")
        }
        _status.value = apiResponseStatus
    }

}