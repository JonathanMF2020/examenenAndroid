package com.jonathanmojica.examenandroid.ui.view.mapa

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jonathanmojica.examenandroid.model.data.DataRequest
import com.jonathanmojica.examenandroid.model.login.LoginResponse
import com.jonathanmojica.examenandroid.service.ApiRepository
import com.jonathanmojica.examenandroid.utils.Constants
import com.jonathanmojica.examenandroid.utils.Resource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MapaViewModel(private val api: ApiRepository): ViewModel() {
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    var getData = MutableLiveData<Resource<LoginResponse>>()

    fun sendData(sendRequest: DataRequest){
        compositeDisposable.add(
            api.sendData(sendRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    if(response.result == Constants.RESPUESTA_EXITOSA)
                    {
                        getData.postValue(Resource.success(response))
                    }
                    else
                    {
                        getData.postValue(Resource.error("Ocurrio un error ", null))
                    }
                }, { throwable ->
                    throwable.printStackTrace()
                    getData.postValue(Resource.error("Ocurrio un error", null))
                })
        )
    }

}