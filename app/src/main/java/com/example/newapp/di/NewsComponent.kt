package com.example.newapp.di

import android.app.Activity
import com.example.newapp.NewsListActivity
import com.example.newapp.NewsListRepository
import dagger.Component

@NewsScope
@Component(modules =  [NewsModule::class, ViewModelModule::class])
interface NewsComponent {
     fun inject(activity: NewsListActivity)
}