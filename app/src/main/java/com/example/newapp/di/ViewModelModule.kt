package com.example.newapp.di

import androidx.lifecycle.ViewModel
import com.example.newapp.NewsListViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @IntoMap
    @Binds
    @NewsScope
    @ViewModelKey(NewsListViewModel::class)
    abstract fun getNewListModel(viewModel: NewsListViewModel):ViewModel
}