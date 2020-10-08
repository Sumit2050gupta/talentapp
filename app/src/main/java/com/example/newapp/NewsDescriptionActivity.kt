package com.example.newapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.newapp.model.Article
import com.nostra13.universalimageloader.core.ImageLoader
import kotlinx.android.synthetic.main.activity_news_description.*
import kotlinx.android.synthetic.main.activity_news_description.heading
import kotlinx.android.synthetic.main.activity_news_description.source

class NewsDescriptionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_description)
        setTitle("Description Page")
        val data = intent.getParcelableExtra<Article>("data")
        data?.let {
            heading.text = it.heading
            ImageLoader.getInstance().displayImage(it.image, image)
            descrition.text = it.description
            source.text = it.source?.name
            published.text = it.published
        }
    }
}