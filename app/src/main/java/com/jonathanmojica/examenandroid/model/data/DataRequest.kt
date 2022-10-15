package com.jonathanmojica.examenandroid.model.data

import com.google.gson.annotations.SerializedName

data class DataRequest(
    @field:SerializedName("latitud")
    val latitud: String? = null,
    @field:SerializedName("longitud")
    val longitud: String? = null,
)
