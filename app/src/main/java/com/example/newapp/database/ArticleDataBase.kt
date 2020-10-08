package com.example.newapp.database

import android.content.Context
import android.content.SharedPreferences
import android.provider.Settings
import com.example.newapp.model.Article
import kotlinx.coroutines.GlobalScope
import javax.inject.Inject

class ArticleDataBase @Inject constructor(context: Context, private val articleDao: ArticleDao) {
    private val sharedPreferences :SharedPreferences
    private var time : Long

    init {
        sharedPreferences = context.getSharedPreferences("ArticleDatabase",Context.MODE_PRIVATE)
        time = 2 * 60 * 60 * 1000
    }

    suspend fun getList() : List<Article> {
        sharedPreferences.getLong("time",0).apply {
            val currentTime = System.currentTimeMillis()
            if( currentTime > this && System.currentTimeMillis() - this < time){
                return@getList articleDao.getList()
            }
        }
        delete()
        return mutableListOf()
    }

    suspend fun insertList(list : List<Article>){
        sharedPreferences.edit().apply{
            putLong("time",System.currentTimeMillis())
            apply()
        }
        articleDao.insertList(list)
    }

    suspend fun delete(){
        articleDao.delete()
    }


}