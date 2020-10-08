package com.example.newapp.database

import android.content.Context
import android.content.SharedPreferences
import com.example.newapp.NewsListRepository
import com.example.newapp.model.Article
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

class ArticleDataBaseTest {

    lateinit var dataBase: ArticleDataBase
    val sharedPreferences = mockk<SharedPreferences>()
    val dao = mockk<ArticleDao>()
    @Before
    fun setUp() {
        val context = mockk<Context>()
        every {  context.getSharedPreferences("ArticleDatabase",Context.MODE_PRIVATE)} returns sharedPreferences
        dataBase = ArticleDataBase(context,dao)
    }

    @Test
    fun `get list when time is less then 2 hours`() {
        runBlocking {
            val list = mutableListOf<Article>()
            every { sharedPreferences.getLong("time",any()) } returns (System.currentTimeMillis() - 3600000)
            coEvery { dao.getList() } returns list
            val result = dataBase.getList()
            assert(list == result)
        }
    }

    @Test
    fun `get list when time is greater than 2 hours`() {
        runBlocking {
            every { sharedPreferences.getLong("time",any()) } returns (System.currentTimeMillis() - 7800000)
            coEvery { dao.delete() } just Runs
            val result = dataBase.getList()
            assert(result.size == 0)
            coVerify { dao.delete() }
        }
    }

    @Test
    fun insertList() {
        runBlocking {
            val list = mutableListOf<Article>()
            every { sharedPreferences.edit() } returns mockk{
                every { putLong("time",any()) } returns mockk()
                every { apply() } just runs
            }
            coEvery { dao.insertList(list) } just Runs
            dataBase.insertList(list)
            coVerify { dao.insertList(list) }
        }
    }

    @Test
    fun delete() {
        runBlocking {
            coEvery { dao.delete() } just Runs
            dataBase.delete()
        }
    }
}