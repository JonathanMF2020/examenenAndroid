package com.jonathanmojica.examenandroid.service

import com.jonathanmojica.examenandroid.model.data.DataRequest
import com.jonathanmojica.examenandroid.model.login.LoginResponse
import io.reactivex.Single

class ApiRepository(private val api: ApiService) {
    fun login(email:String,password:String): Single<LoginResponse> =
        api.getlogin(email,password)

    fun sendData(sendRequest: DataRequest): Single<LoginResponse> =
        api.sendData(sendRequest)
}