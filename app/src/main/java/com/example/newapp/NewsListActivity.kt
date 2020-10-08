package com.example.newapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.newapp.di.DaggerNewsComponent
import com.example.newapp.di.NewsModule
import com.example.newapp.di.ViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class NewsListActivity : AppCompatActivity() {
    private lateinit var adapter: NewsListAdapter

    @Inject
    lateinit var factory: ViewModelFactory
    val mViewModel by lazy {
        ViewModelProviders.of(this, factory).get(NewsListViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        DaggerNewsComponent.builder().newsModule(NewsModule(this)).build().inject(this)
        container.displayedChild = PROGRESS_VIEW
        if (savedInstanceState == null) {
            mViewModel.loadPage(1)
        }
        mViewModel.listLiveData.observe(this, Observer {
            when (it) {
                is Success -> {

                    if (!::adapter.isInitialized) {
                        container.displayedChild = DATA_VIEW
                        adapter = NewsListAdapter(it.data, it.page, this::loadPage)
                        list_view.adapter = adapter
                    } else {
                        adapter.onLoad(it.page, it.data)
                    }
                    if (it.isLastPage) {
                        adapter.onLoadComplete()
                    }
                }
                is ErrorMessage -> {
                    showError(it.data)
                }
            }
        })
        retry.setOnClickListener {
            loadPage(1)
            container.displayedChild = PROGRESS_VIEW
        }
    }

    private fun showError(errorMessage: String) {
        if (!::adapter.isInitialized) {
            message.text = "Something went Wrong"
            container.displayedChild = ERROR_VIEW
        }
    }

    fun loadPage(page: Int) {
        mViewModel.loadPage(page)
    }

    companion object {
        const val PROGRESS_VIEW = 1
        const val DATA_VIEW = 0
        const val ERROR_VIEW = 2
    }

}