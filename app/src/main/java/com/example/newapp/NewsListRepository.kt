package com.example.newapp

import android.util.Log
import com.example.newapp.database.ArticleDataBase
import com.example.newapp.model.Article
import com.example.newapp.network.RetrofitService
import kotlinx.coroutines.*
import java.lang.Exception
import java.lang.NullPointerException
import javax.inject.Inject

class NewsListRepository @Inject constructor(
    private val webService: RetrofitService,
    private val articleDataBase: ArticleDataBase
) {

    private var isFromDatabase = false

    private val queryMap: MutableMap<String, String>
        get() = mutableMapOf(
            Pair("pageSize", "10"), Pair("apiKey", "a0a6c06f39714f72bef9c69ea2da629b"),
            Pair("country", "us")
        )


    private suspend fun loadPage(page: Int) = webService.getList(queryMap.apply { plusAssign(Pair("page", page.toString())) })


    fun getData(
        onSuccess: (Int,List<Article>) -> Unit,
        onError: (Int,Exception) -> Unit,
        page: Int,
        scope: CoroutineScope
    ) {
        scope.launch {
            try {
                if (page > 1 && isFromDatabase) {
                    onError(page,NullPointerException("not data found"))
                    return@launch
                }
                val response = loadPage(page)
                if (response.isSuccessful) {
                    onSuccess(page,response.body()?.articles ?: mutableListOf())
                    isFromDatabase = false
                    GlobalScope.launch(Dispatchers.IO) {
                        if (page == 1) articleDataBase.delete()
                        articleDataBase.insertList(response.body()?.articles ?: mutableListOf())
                    }
                } else {
                    Log.e("NewsRepository", response.toString())
                    onError(page,onSuccess,onError)
                }
            }catch (e : Exception){
                onError(page,onSuccess,onError)
            }
        }
    }

    private suspend fun onError(
        page: Int,
        onSuccess: (Int,List<Article>) -> Unit,
        onError: (Int, Exception) -> Unit
    ) {
        if (page == 1) {
            val data = articleDataBase.getList()
            onSuccess(page,data)
            isFromDatabase = true
        } else {
            onError(page,NullPointerException("network error"))
        }
    }


}
