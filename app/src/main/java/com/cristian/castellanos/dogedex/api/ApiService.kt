package com.cristian.castellanos.dogedex.api

import com.cristian.castellanos.dogedex.*
import com.cristian.castellanos.dogedex.api.dto.AddDogToUserDTO
import com.cristian.castellanos.dogedex.api.dto.LoginDTO
import com.cristian.castellanos.dogedex.api.dto.SignUpDTO
import com.cristian.castellanos.dogedex.api.responses.AuthApiResponse
import com.cristian.castellanos.dogedex.api.responses.DefaultResponse
import com.cristian.castellanos.dogedex.api.responses.DogListApiResponse
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

private val okHttpClient = OkHttpClient
    .Builder()
    .addInterceptor(ApiServiceInterceptor)
    .build()

private val retrofit =
    Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

interface ApiService {
    @GET(GET_ALL_DOGS_URL)
    suspend fun getAllDogs(): DogListApiResponse

    @POST(SIGN_UP_URL)
    suspend fun signUp(@Body signUpDTO: SignUpDTO): AuthApiResponse

    @POST(SIGN_IN_URL)
    suspend fun login(@Body loginDTO: LoginDTO): AuthApiResponse

    @Headers("${ApiServiceInterceptor.NEEDS_AUTH_HEADER_KEY}: true")
    @POST(ADD_DOG_TO_USER_URL)
    suspend fun addDogToUser(@Body addDogToUserDTO: AddDogToUserDTO): DefaultResponse

    @Headers("${ApiServiceInterceptor.NEEDS_AUTH_HEADER_KEY}: true")
    @GET(GET_USER_DOGS_URL)
    suspend fun getUserDogs(): DogListApiResponse
}

object DogsApi {
    val retrofitServices: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}