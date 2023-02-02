package com.cristian.castellanos.dogedex.auth

import com.cristian.castellanos.dogedex.api.ApiResponseStatus
import com.cristian.castellanos.dogedex.model.User

interface AuthTasks {
    suspend fun login(email: String, password: String): ApiResponseStatus<User>
    suspend fun signUp(
        email: String,
        password: String,
        passwordConfirmation: String
    ): ApiResponseStatus<User>
}