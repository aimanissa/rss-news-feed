package com.aimanissa.android.newsfeed

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import java.util.*

class NewsFeedFragment : Fragment() {

    interface Callbacks {
        fun newsItemSelected(newsItem: NewsItem)
    }

    private var callbacks: Callbacks? = null
    private lateinit var newsFeedViewModel: NewsFeedViewModel
    private lateinit var newsRecyclerView: RecyclerView

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true

        newsFeedViewModel =
            ViewModelProvider(this@NewsFeedFragment).get(NewsFeedViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_news_feed, container, false)

        newsRecyclerView = view.findViewById(R.id.recycler_view)
        newsRecyclerView.layoutManager = LinearLayoutManager(context)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        newsFeedViewModel.newsItemLiveData.observe(
            viewLifecycleOwner,
            Observer { newsItems ->
                newsRecyclerView.adapter = NewsAdapter(newsItems)
            }
        )
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    private inner class NewsHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        private lateinit var newsItem: NewsItem

        private val newsImage: ImageView = view.findViewById(R.id.news_image_view)
        private val newsTitle: TextView = view.findViewById(R.id.news_title)
        private val newsDescription: TextView = view.findViewById(R.id.news_description)

        init {
            view.setOnClickListener(this)
        }

        fun bindNewsItem(newsItem: NewsItem) {
            this.newsItem = newsItem
            Picasso.get()
                .load(newsItem.urlToImage)
                .into(newsImage)

            newsTitle.text = newsItem.title
            newsDescription.text = newsItem.description
        }

        override fun onClick(v: View?) {
            callbacks?.newsItemSelected(newsItem)
        }
    }

    private inner class NewsAdapter(private val newsItems: List<NewsItem>) :
        RecyclerView.Adapter<NewsHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {
            val view = layoutInflater.inflate(R.layout.news_item, parent, false)
            return NewsHolder(view)
        }

        override fun onBindViewHolder(holder: NewsHolder, position: Int) {
            val newsItem = newsItems[position]
            holder.bindNewsItem(newsItem)
        }

        override fun getItemCount(): Int = newsItems.size

    }

    companion object {
        private const val TAG = "NewsFeedFragment"

        fun newInstance() = NewsFeedFragment()
    }
}