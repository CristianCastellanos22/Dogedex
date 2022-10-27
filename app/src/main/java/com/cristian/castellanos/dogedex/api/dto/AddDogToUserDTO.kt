package com.cristian.castellanos.dogedex.api.dto

import com.google.gson.annotations.SerializedName

class AddDogToUserDTO(
    @SerializedName("dog_id")
    val dogId: String
)