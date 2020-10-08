package com.example.newapp

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newapp.model.Article
import com.nostra13.universalimageloader.core.DisplayImageOptions
import com.nostra13.universalimageloader.core.ImageLoader
import kotlinx.android.synthetic.main.list_item.view.*

class NewsListAdapter(private var list : List<Article>,private var page :Int,private val load : (Int)-> Unit):RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    init {
        page++
    }
    private var isLastPage = false
    private var isLoading = false

    val  options = DisplayImageOptions.Builder().run {
        resetViewBeforeLoading(true) // default
        cacheInMemory(true) // default
        considerExifParams(false) // default
        build();
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == SHOW_PROGRESS){
            ProgressView(LayoutInflater.from(parent.context).inflate(R.layout.progress_view,parent,false))
        } else {
            NewsListHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false))
        }
    }

    override fun getItemCount(): Int {
        return if (isLastPage) list.size else (list.size + 1)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is ProgressView -> {
                if (!isLoading) {
                    load(page)
                    isLoading = true
                }
            }
            is NewsListHolder -> {
                val item = list[position]
                holder.itemView.apply {
                    heading.text = item.heading
                    published.text = item.published
                    source.text = item.source?.name
                    ImageLoader.getInstance().displayImage(item.image,imageView,options)
                    setOnClickListener{
                        it.context.startActivity(Intent(it.context,NewsDescriptionActivity::class.java).apply { putExtra("data",item) })
                    }
                }
            }
        }
    }

    fun onLoad(page :Int ,list: List<Article>){
        this.list = list
        notifyDataSetChanged()
        isLoading = false
        this.page = page + 1
    }

    fun onLoadComplete(){
        isLastPage = true
        isLoading = false
        page++
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return if (position >= list.size) SHOW_PROGRESS else SHOW_CARD
    }

    companion object{
        private const val SHOW_PROGRESS = 1
        private const val SHOW_CARD = 2
    }

    class NewsListHolder(view : View):RecyclerView.ViewHolder(view)

    class ProgressView(view : View):RecyclerView.ViewHolder(view)
}