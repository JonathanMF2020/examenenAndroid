package com.jonathanmojica.examenandroid.model.login

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("result")
    val result: Int? = null,

    @SerializedName("code")
    val code: String? = null,
)
