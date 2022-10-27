package com.cristian.castellanos.dogedex.api.dto

import com.google.gson.annotations.SerializedName

data class SignUpDTO(
    val email: String,
    val password: String,
    @SerializedName("password_confirmation")
    val passwordConfirmation: String
)