package com.example.newapp

import com.example.newapp.database.ArticleDataBase
import com.example.newapp.network.RetrofitService
import io.mockk.*
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import org.junit.Before
import org.junit.Test

class NewsListRepositoryTest {

    lateinit var repository: NewsListRepository
    val service = mockk<RetrofitService>()
    val dataBase = mockk<ArticleDataBase>()
    val testScope = TestCoroutineScope(TestCoroutineDispatcher())

    @Before
    fun setUp() {
        repository = NewsListRepository(service, dataBase)
    }

    @Test
    fun `get data for page 1 when internet on`() {
        var s = 0
        var e = 0
        coEvery { service.getList(any()) } returns mockk {
            every { isSuccessful } returns true
            every { body() } returns mockk {
                every { articles } returns mockk()
            }
        }
        coEvery { dataBase.delete() } just Runs
        coEvery { dataBase.insertList(any()) } just Runs
        repository.getData(onSuccess = { p, l ->
            if (p == 1)
            s++
        }, onError = { p, ex ->
            e++
        }, page = 1, scope = testScope)

        assert(s == 1)
        assert(e == 0)
        coVerify { service.getList(any()) }
    }

    @Test
    fun `get data for page greater then 1 when internet on`() {
        var s = 0
        var e = 0
        coEvery { service.getList(any()) } returns mockk {
            every { isSuccessful } returns true
            every { body() } returns mockk {
                every { articles } returns mockk()
            }
        }
        coEvery { dataBase.insertList(any()) } just Runs
        repository.getData(onSuccess = { p, l ->
            if(p == 2)
            s++
        }, onError = { p, ex ->
            e++
        }, page = 2, scope = testScope)

        assert(s == 1)
        assert(e == 0)
        coVerify { service.getList(any()) }
    }

    @Test
    fun `get data form db when internet off`() {
        var s = 0
        var e = 0
        coEvery { service.getList(any()) } returns mockk {
            every { isSuccessful } returns false
            every { body() } returns mockk {
                every { articles } returns mockk()
            }
        }
        coEvery { dataBase.getList() } returns mockk()
        repository.getData(onSuccess = { p, l ->
            if(p == 1)
                s++
        }, onError = { p, ex ->
            e++
        }, page = 1, scope = testScope)

        assert(s == 1)
        assert(e == 0)
        coVerify { service.getList(any()) }
        coVerify { dataBase.getList() }
    }

    @Test
    fun `get data for page 2 when internet off`() {
        var s = 0
        var e = 0
        coEvery { service.getList(any()) } returns mockk {
            every { isSuccessful } returns false
            every { body() } returns mockk {
                every { articles } returns mockk()
            }
        }
        coEvery { dataBase.getList() } returns mockk()
        repository.getData(onSuccess = { p, l ->
            if(p == 1)
                s++
        }, onError = { p, ex ->
            e++
        }, page = 2, scope = testScope)

        assert(s == 0)
        assert(e == 1)
        coVerify { service.getList(any()) }
    }

}