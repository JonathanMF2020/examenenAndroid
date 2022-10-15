package com.jonathanmojica.examenandroid.utils

import android.content.Context
import android.widget.Toast
import com.jonathanmojica.examenandroid.BuildConfig
import com.jonathanmojica.examenandroid.service.ApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class Settings {

    companion object {
        fun getRetrofit(): ApiService {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            val httpClient = OkHttpClient.Builder()
            httpClient.addInterceptor(logging);
            val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.URL_BASE)
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

            return retrofit.create(ApiService::class.java)
        }
    }

    fun Context.toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}