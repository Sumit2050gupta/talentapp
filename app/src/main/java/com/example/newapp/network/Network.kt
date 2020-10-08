package com.example.newapp.network

import android.app.Application
import com.google.gson.Gson
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.NullPointerException

class Network private constructor(private val mRetrofit: Retrofit){


    fun getRetrofitService() = mRetrofit.create(RetrofitService::class.java)

    companion object{
        private lateinit var INSTANCE : Network
        private const val BASE_URL = "http://newsapi.org"

        fun getInstance() : Network{
            if (::INSTANCE.isInitialized){
                return INSTANCE
            }
            throw NullPointerException("retrofit is not inisiatlized")
        }
        fun init(){
            if (!::INSTANCE.isInitialized) {
                INSTANCE = Network(
                    Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                )
            }
        }
    }
}