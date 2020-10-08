package com.example.newapp

import android.app.Application
import android.content.Context
import com.example.newapp.database.AppDatabase
import com.example.newapp.network.Network
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration
import com.nostra13.universalimageloader.core.assist.QueueProcessingType

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        AppDatabase.init(this)
        Network.init()
        initImageLoader(context = this)
    }

    fun initImageLoader(context: Context?) {

        // This configuration tuning is custom. You can tune every option, you may tune some of them,
        // or you can create default configuration by the
        //  ImageLoaderConfiguration.createDefault(this);
        // method.
        //
        val config = ImageLoaderConfiguration.Builder(context)
        config.threadPriority(Thread.NORM_PRIORITY - 2)
        config.diskCacheSize(100 * 1024 * 1024) // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO)
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config.build())
    }
}