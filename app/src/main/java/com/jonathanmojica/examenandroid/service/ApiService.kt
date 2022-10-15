package com.jonathanmojica.examenandroid.service

import com.jonathanmojica.examenandroid.model.data.DataRequest
import com.jonathanmojica.examenandroid.model.login.LoginResponse
import io.reactivex.Single
import retrofit2.http.*


interface ApiService {

    @POST("login/")
    fun getlogin(
        @Query("email") email: String,
        @Query("password") password: String,
    ): Single<LoginResponse>

    @POST("sendData/")
    fun sendData(
        @Body sendRequest: DataRequest
    ): Single<LoginResponse>
}