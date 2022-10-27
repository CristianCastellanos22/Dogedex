package com.cristian.castellanos.dogedex.doglist

import com.cristian.castellanos.dogedex.model.Dog
import com.cristian.castellanos.dogedex.api.ApiResponseStatus
import com.cristian.castellanos.dogedex.api.DogsApi.retrofitServices
import com.cristian.castellanos.dogedex.api.dto.DogDTOMapper
import com.cristian.castellanos.dogedex.api.makeNetworkCall

class DogRepository {
    suspend fun downLoadDogs(): ApiResponseStatus<List<Dog>> =
        makeNetworkCall {
            val dogListApiResponse = retrofitServices.getAllDogs()
            val dogDTOList = dogListApiResponse.data.dogs
            val dogDTOMapper = DogDTOMapper()
            dogDTOMapper.fromDogDTOListToDogDomainList(dogDTOList)
        }

}