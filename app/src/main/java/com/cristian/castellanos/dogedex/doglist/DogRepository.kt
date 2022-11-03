package com.cristian.castellanos.dogedex.doglist

import com.cristian.castellanos.dogedex.R
import com.cristian.castellanos.dogedex.api.ApiResponseStatus
import com.cristian.castellanos.dogedex.api.DogsApi.retrofitServices
import com.cristian.castellanos.dogedex.api.dto.AddDogToUserDTO
import com.cristian.castellanos.dogedex.api.dto.DogDTOMapper
import com.cristian.castellanos.dogedex.api.makeNetworkCall
import com.cristian.castellanos.dogedex.model.Dog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class DogRepository {

    suspend fun getDogCollection(): ApiResponseStatus<List<Dog>> {
        return withContext(Dispatchers.IO) {
            val allDogsListDeferred = async { downLoadDogs() }
            val userDogsListDeferred = async { getUserDogs() }

            val allDogsListResponse = allDogsListDeferred.await()
            val userDogsListResponse = userDogsListDeferred.await()

            when {
                allDogsListResponse is ApiResponseStatus.Error -> {
                    allDogsListResponse
                }
                userDogsListResponse is ApiResponseStatus.Error -> {
                    userDogsListResponse
                }
                allDogsListResponse is ApiResponseStatus.Success && userDogsListResponse is ApiResponseStatus.Success -> {
                    ApiResponseStatus.Success(
                        getCollectionList(
                            allDogsListResponse.data,
                            userDogsListResponse.data
                        )
                    )
                }
                else -> {
                    ApiResponseStatus.Error(R.string.unknown_error)
                }
            }
        }
    }

    private fun getCollectionList(allDogList: List<Dog>, userDogList: List<Dog>) =
        allDogList.map {
            if (userDogList.contains(it)) {
                it
            } else {
                Dog(
                    0, it.index, "", "", "", "", "",
                    "", "", "", "", inCollection = false
                )
            }
        }.sorted()

    private suspend fun downLoadDogs(): ApiResponseStatus<List<Dog>> = makeNetworkCall {
        val dogListApiResponse = retrofitServices.getAllDogs()
        val dogDTOList = dogListApiResponse.data.dogs
        val dogDTOMapper = DogDTOMapper()
        dogDTOMapper.fromDogDTOListToDogDomainList(dogDTOList)
    }

    suspend fun addDogToUser(dogId: Long): ApiResponseStatus<Any> = makeNetworkCall {
        val addDogToUserDTO = AddDogToUserDTO(dogId)
        val defaultResponse = retrofitServices.addDogToUser(addDogToUserDTO)
        if (!defaultResponse.isSuccess) {
            throw Exception(defaultResponse.message)
        }
    }

    private suspend fun getUserDogs(): ApiResponseStatus<List<Dog>> = makeNetworkCall {
        val dogListApiResponse = retrofitServices.getUserDogs()
        val dogDTOList = dogListApiResponse.data.dogs
        val dogDTOMapper = DogDTOMapper()
        dogDTOMapper.fromDogDTOListToDogDomainList(dogDTOList)
    }

    suspend fun getDogByMlId(mlDogId: String): ApiResponseStatus<Dog> = makeNetworkCall {
        val response = retrofitServices.getDogByMlId(mlDogId)
        if (!response.isSuccess) {
            throw Exception(response.message)
        }
        val dogDTOMapper = DogDTOMapper()
        dogDTOMapper.fromDogDTODogToDomain(response.data.dog)
    }

}