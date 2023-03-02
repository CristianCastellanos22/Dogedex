package com.cristian.castellanos.dogedex

import com.cristian.castellanos.dogedex.api.ApiResponseStatus
import com.cristian.castellanos.dogedex.api.ApiService
import com.cristian.castellanos.dogedex.api.dto.AddDogToUserDTO
import com.cristian.castellanos.dogedex.api.dto.DogDTO
import com.cristian.castellanos.dogedex.api.dto.LoginDTO
import com.cristian.castellanos.dogedex.api.dto.SignUpDTO
import com.cristian.castellanos.dogedex.api.responses.*
import com.cristian.castellanos.dogedex.doglist.DogRepository
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Assert.assertEquals
import org.junit.Test
import java.net.UnknownHostException

class DogRepository {

    @Test
    fun testGetDogCollectionSuccess(): Unit = runBlocking {
        class FakeApiServices : ApiService {
            override suspend fun getAllDogs(): DogListApiResponse {
                return DogListApiResponse(
                    message = "",
                    isSuccess = true,
                    data = DogListResponse(
                        dogs = listOf(
                            DogDTO(
                                1, 1, "", "", "", "", "",
                                "", "", "", ""
                            ),
                            DogDTO(
                                19, 2, "", "", "", "", "",
                                "", "", "", ""
                            )
                        )
                    )
                )
            }

            override suspend fun signUp(signUpDTO: SignUpDTO): AuthApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun login(loginDTO: LoginDTO): AuthApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun addDogToUser(addDogToUserDTO: AddDogToUserDTO): DefaultResponse {
                TODO("Not yet implemented")
            }

            override suspend fun getUserDogs(): DogListApiResponse {
                return DogListApiResponse(
                    message = "",
                    isSuccess = true,
                    data = DogListResponse(
                        dogs = listOf(
                            DogDTO(
                                19, 2, "", "", "", "", "",
                                "", "", "", ""
                            )
                        )
                    )
                )
            }

            override suspend fun getDogByMlId(mlId: String): DogApiApiResponse {
                TODO("Not yet implemented")
            }

        }

        val dogRepository = DogRepository(
            apiService = FakeApiServices(),
            dispatcher = TestCoroutineDispatcher()
        )

        val apiResponseStatus = dogRepository.getDogCollection()
        assert(apiResponseStatus is ApiResponseStatus.Success)

        val dogCollection = (apiResponseStatus as ApiResponseStatus.Success).data
        assertEquals(2, dogCollection.size)
    }

    @Test
    fun testGetAllDogsError(): Unit = runBlocking {
        class FakeApiServices : ApiService {
            override suspend fun getAllDogs(): DogListApiResponse {
                throw UnknownHostException()
            }

            override suspend fun signUp(signUpDTO: SignUpDTO): AuthApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun login(loginDTO: LoginDTO): AuthApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun addDogToUser(addDogToUserDTO: AddDogToUserDTO): DefaultResponse {
                TODO("Not yet implemented")
            }

            override suspend fun getUserDogs(): DogListApiResponse {
                return DogListApiResponse(
                    message = "",
                    isSuccess = true,
                    data = DogListResponse(
                        dogs = listOf(
                            DogDTO(
                                19, 2, "", "", "", "", "",
                                "", "", "", ""
                            )
                        )
                    )
                )
            }

            override suspend fun getDogByMlId(mlId: String): DogApiApiResponse {
                TODO("Not yet implemented")
            }

        }

        val dogRepository = DogRepository(
            apiService = FakeApiServices(),
            dispatcher = TestCoroutineDispatcher()
        )

        val apiResponseStatus = dogRepository.getDogCollection()
        assert(apiResponseStatus is ApiResponseStatus.Error)
        assertEquals(R.string.unknown_host_exception_error,
            (apiResponseStatus as ApiResponseStatus.Error).messageId)

    }

    @Test
    fun getDogByMlSuccess(): Unit = runBlocking {
        class FakeApiServices : ApiService {
            override suspend fun getAllDogs(): DogListApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun signUp(signUpDTO: SignUpDTO): AuthApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun login(loginDTO: LoginDTO): AuthApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun addDogToUser(addDogToUserDTO: AddDogToUserDTO): DefaultResponse {
                TODO("Not yet implemented")
            }

            override suspend fun getUserDogs(): DogListApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun getDogByMlId(mlId: String): DogApiApiResponse {
                return DogApiApiResponse(
                    message = "",
                    isSuccess = true,
                    data = DogResponse(
                        dog = DogDTO(
                            19, 2, "", "", "", "", "",
                            "", "", "", ""
                        )
                    )
                )
            }
        }

        val dogRepository = DogRepository(
            apiService = FakeApiServices(),
            dispatcher = TestCoroutineDispatcher()
        )

        val apiResponseStatus = dogRepository.getDogByMlId("19")
        assert(apiResponseStatus is ApiResponseStatus.Success)
        assertEquals(19, (apiResponseStatus as ApiResponseStatus.Success).data.id)
    }

    @Test
    fun getDogByMlError(): Unit = runBlocking {
        class FakeApiServices : ApiService {
            override suspend fun getAllDogs(): DogListApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun signUp(signUpDTO: SignUpDTO): AuthApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun login(loginDTO: LoginDTO): AuthApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun addDogToUser(addDogToUserDTO: AddDogToUserDTO): DefaultResponse {
                TODO("Not yet implemented")
            }

            override suspend fun getUserDogs(): DogListApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun getDogByMlId(mlId: String): DogApiApiResponse {
                return DogApiApiResponse(
                    message = "Error_get_dog",
                    isSuccess = false,
                    data = DogResponse(
                        dog = DogDTO(
                            19, 2, "", "", "", "", "",
                            "", "", "", ""
                        )
                    )
                )
            }
        }

        val dogRepository = DogRepository(
            apiService = FakeApiServices(),
            dispatcher = TestCoroutineDispatcher()
        )

        val apiResponseStatus = dogRepository.getDogByMlId("19")
        assert(apiResponseStatus is ApiResponseStatus.Error)
        assertEquals(R.string.unknown_error, (apiResponseStatus as ApiResponseStatus.Error).messageId)
    }


}