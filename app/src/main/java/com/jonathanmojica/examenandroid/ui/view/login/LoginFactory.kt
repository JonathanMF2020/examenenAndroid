package com.jonathanmojica.examenandroid.ui.view.login

import android.os.Parcel
import android.os.Parcelable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jonathanmojica.examenandroid.service.ApiRepository


class LoginFactory(
    private val repository: ApiRepository,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(LoginViewModel::class.java)){
            LoginViewModel(
                repository,
            ) as T
        }else throw IllegalArgumentException("No se encontró la clase")
    }
}

