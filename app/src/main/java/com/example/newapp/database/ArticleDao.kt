package com.example.newapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.newapp.model.Article


@Dao
interface ArticleDao {
    @Query("SELECT * FROM article")
    suspend fun getList() : List<Article>

    @Insert
    suspend fun insertList(lsit : List<Article>)

    @Query("DELETE  FROM  article")
    suspend fun delete()
}