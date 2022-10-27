package com.cristian.castellanos.dogedex.api.dto

import com.google.gson.annotations.SerializedName

data class LoginDTO(
    val email: String,
    val password: String
)