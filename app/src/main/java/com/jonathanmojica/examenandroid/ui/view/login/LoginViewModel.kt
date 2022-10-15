package com.jonathanmojica.examenandroid.ui.view.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jonathanmojica.examenandroid.model.login.LoginResponse
import com.jonathanmojica.examenandroid.service.ApiRepository
import com.jonathanmojica.examenandroid.utils.Constants
import com.jonathanmojica.examenandroid.utils.Resource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class LoginViewModel(private val api: ApiRepository): ViewModel() {
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    var getLogin = MutableLiveData<Resource<LoginResponse>>()

    fun login(email:String,password:String){
        compositeDisposable.add(
            api.login(email,password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    if(response.result == Constants.RESPUESTA_EXITOSA)
                    {
                        getLogin.postValue(Resource.success(response))
                    }
                    else
                    {
                        getLogin.postValue(Resource.error("Ocurrio un error ", null))
                    }
                }, { throwable ->
                    throwable.printStackTrace()
                    getLogin.postValue(Resource.error("Ocurrio un error", null))
                })
        )
    }
}