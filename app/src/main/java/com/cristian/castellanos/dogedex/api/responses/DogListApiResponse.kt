package com.cristian.castellanos.dogedex.api.responses

import com.google.gson.annotations.SerializedName

data class DogListApiResponse(
    val message: String,
    @SerializedName("is_success")
    val isSuccess: Boolean,
    val data: DogListResponse
)