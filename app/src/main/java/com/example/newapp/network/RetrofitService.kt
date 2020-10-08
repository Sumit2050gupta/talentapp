package com.example.newapp.network

import com.example.newapp.model.ArticleOuter
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface RetrofitService {
    @GET("/v2/top-headlines")
    suspend fun getList(@QueryMap map : Map<String,String>): Response<ArticleOuter>
}
