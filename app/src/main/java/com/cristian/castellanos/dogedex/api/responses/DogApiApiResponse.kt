package com.cristian.castellanos.dogedex.api.responses

import com.cristian.castellanos.dogedex.api.dto.DogDTO
import com.google.gson.annotations.SerializedName

data class DogApiApiResponse(
    val message: String,
    @SerializedName("is_success")
    val isSuccess: Boolean,
    val data: DogResponse
)