package com.example.newapp.model

import com.google.gson.annotations.SerializedName

data class ArticleOuter(
    @SerializedName("status")
    val status:String? = null,
    @SerializedName("articles")
    val articles: List<Article>? = null)