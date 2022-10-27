package com.cristian.castellanos.dogedex.api

import com.cristian.castellanos.dogedex.BASE_URL
import com.cristian.castellanos.dogedex.GET_ALL_DOGS_URL
import com.cristian.castellanos.dogedex.SIGN_IN_URL
import com.cristian.castellanos.dogedex.SIGN_UP_URL
import com.cristian.castellanos.dogedex.api.dto.LoginDTO
import com.cristian.castellanos.dogedex.api.dto.SignUpDTO
import com.cristian.castellanos.dogedex.api.responses.DogListApiResponse
import com.cristian.castellanos.dogedex.api.responses.AuthApiResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

private val retrofit =
    Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()

interface ApiService {
    @GET(GET_ALL_DOGS_URL)
    suspend fun getAllDogs(): DogListApiResponse

    @POST(SIGN_UP_URL)
    suspend fun signUp(@Body signUpDTO: SignUpDTO): AuthApiResponse

    @POST(SIGN_IN_URL)
    suspend fun login(@Body loginDTO: LoginDTO): AuthApiResponse
}

object DogsApi {
    val retrofitServices: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}