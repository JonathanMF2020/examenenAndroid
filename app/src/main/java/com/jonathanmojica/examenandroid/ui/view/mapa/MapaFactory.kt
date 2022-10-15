package com.jonathanmojica.examenandroid.ui.view.mapa

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jonathanmojica.examenandroid.service.ApiRepository

class MapaFactory(
    private val repository: ApiRepository,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MapaViewModel::class.java)){
            MapaViewModel(
                repository,
            ) as T
        }else throw IllegalArgumentException("No se encontró la clase")
    }
}
