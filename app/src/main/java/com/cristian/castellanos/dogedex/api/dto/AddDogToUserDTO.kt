package com.cristian.castellanos.dogedex.api.dto

import com.google.gson.annotations.SerializedName

data class AddDogToUserDTO(
    @SerializedName("dog_id")
    val dogId: Long
)