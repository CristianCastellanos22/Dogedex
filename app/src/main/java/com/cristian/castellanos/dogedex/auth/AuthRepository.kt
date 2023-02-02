package com.cristian.castellanos.dogedex.auth

import com.cristian.castellanos.dogedex.api.ApiResponseStatus
import com.cristian.castellanos.dogedex.api.ApiService
import com.cristian.castellanos.dogedex.api.DogsApi
import com.cristian.castellanos.dogedex.api.dto.LoginDTO
import com.cristian.castellanos.dogedex.api.dto.SignUpDTO
import com.cristian.castellanos.dogedex.api.dto.UserDTOMapper
import com.cristian.castellanos.dogedex.api.makeNetworkCall
import com.cristian.castellanos.dogedex.model.User
import javax.inject.Inject

class AuthRepository @Inject constructor( private val apiService: ApiService) : AuthTasks{

    override suspend fun login(email: String, password: String): ApiResponseStatus<User> = makeNetworkCall {
        val loginDTO = LoginDTO(email, password)
        val loginResponse = apiService.login(loginDTO)
        if (!loginResponse.isSuccess) {
            throw Exception(loginResponse.message)
        }
        val userDTO = loginResponse.data.user
        val userDTOMapper = UserDTOMapper()
        userDTOMapper.fromUserDTOToUserDomain(userDTO)

    }

    override suspend fun signUp(
        email: String,
        password: String,
        passwordConfirmation: String
    ): ApiResponseStatus<User> = makeNetworkCall {
        val signUpDTO = SignUpDTO(email, password, passwordConfirmation)
        val signUpResponse = apiService.signUp(signUpDTO)
        if (!signUpResponse.isSuccess) {
            throw Exception(signUpResponse.message)
        }
        val userDTO = signUpResponse.data.user
        val userDTOMapper = UserDTOMapper()
        userDTOMapper.fromUserDTOToUserDomain(userDTO)

    }
}