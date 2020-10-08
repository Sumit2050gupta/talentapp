package com.example.newapp.di

import android.content.Context
import com.example.newapp.database.AppDatabase
import com.example.newapp.network.Network
import dagger.Module
import dagger.Provides

@Module
class NewsModule constructor(private val context: Context) {

    @Provides
    fun getContext() = context

    @Provides
    @NewsScope
    fun getApiService() = Network.getInstance().getRetrofitService()

    @Provides
    @NewsScope
    fun getArticleDao() = AppDatabase.getInstance().articleDao()
}