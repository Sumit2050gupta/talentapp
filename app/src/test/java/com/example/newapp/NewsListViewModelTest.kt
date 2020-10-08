package com.example.newapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.viewModelScope
import com.example.newapp.model.Article
import io.mockk.*
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule

class NewsListViewModelTest {

    lateinit var  viewModel: NewsListViewModel
    private val repository = mockk<NewsListRepository>()

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        viewModel = NewsListViewModel(repository)
    }

    @Test
    fun `loadPage when page 1`() {
        coEvery { repository.getData(viewModel::onSuccess,viewModel::onError,1,viewModel.viewModelScope) } just Runs
        viewModel.page = 1
        viewModel.loadPage(1)
        verify { repository.getData(viewModel::onSuccess,viewModel::onError,1,viewModel.viewModelScope) }
    }

    @Test
    fun `loadPage when page 2 but page 2 already hited`() {
        coEvery { repository.getData(viewModel::onSuccess,viewModel::onError,1,viewModel.viewModelScope) } just Runs
        viewModel.page = 3
        viewModel.loadPage(2)
        verify(exactly = 0) { repository.getData(viewModel::onSuccess,viewModel::onError,1,viewModel.viewModelScope) }
    }

    @Test
    fun `onSuccessfull data`() {
        val list = mutableListOf<Article>()
        list.add(mockk())
        viewModel.onSuccess(1,list)
        val result = viewModel.listLiveData.value as Success
        assert(result.page == 1)
        assert(!result.isLastPage)
        assert(result.data.size == 1)

    }

    @Test
    fun `when data is come with empty list on page greater than one`() {
        val list = mutableListOf<Article>()
        viewModel.list.add(mockk())
        viewModel.onSuccess(2,list)
        val result = viewModel.listLiveData.value as Success
        assert(result.page == 2)
        assert(result.isLastPage)
        assert(result.data.size == 1)
    }

    @Test
    fun `when data is come with empty list on page  one`() {
        val list = mutableListOf<Article>()
        viewModel.onSuccess(1,list)
        assert(viewModel.listLiveData.value is ErrorMessage)
    }
}