package com.example.newapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newapp.model.Article
import javax.inject.Inject

class NewsListViewModel @Inject constructor(private val mRepository: NewsListRepository) :
    ViewModel() {

    val listLiveData = MutableLiveData<Resources<List<Article>>>()
    val list = mutableListOf<Article>()
    var page = 1

    fun loadPage(page: Int) {
        if (page > 1 && this.page >= page) return
        mRepository.getData(this::onSuccess, this::onError, page, viewModelScope)
        this.page = page
    }

     fun onSuccess(page: Int, data: List<Article>) {
        if (data.size > 0) {
            list.addAll(data)
            listLiveData.value = Success(false, page, list)
        } else {
            onError(page, NullPointerException("empty list"))
        }
    }

     fun onError(page: Int, e: Exception) {
        if (list.size > 0) {
            listLiveData.value = Success(true, page, list)
        } else {
            listLiveData.value = ErrorMessage(e.toString())
        }
    }
}


sealed class Resources<T>
data class Success<T>(val isLastPage: Boolean, val page: Int, val data: T) : Resources<T>()
data class ErrorMessage<T>(val data: String) : Resources<T>()