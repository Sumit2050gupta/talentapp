package com.example.newapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.newapp.model.Article
import com.example.newapp.model.Source
import com.example.newapp.network.Network
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.NullPointerException

@Database(entities = arrayOf(Article::class,Source::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao

    companion object{
        private lateinit var INSTANCE : AppDatabase

        fun getInstance() : AppDatabase {
            if (::INSTANCE.isInitialized){
                return INSTANCE
            }
            throw NullPointerException("retrofit is not inisiatlized")
        }
        fun init(context: Context){
            if (!::INSTANCE.isInitialized) {
                INSTANCE = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java, "database-name"
                ).build()
            }
        }
    }
}