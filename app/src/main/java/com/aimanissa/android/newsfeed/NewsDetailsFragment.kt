package com.aimanissa.android.newsfeed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import java.util.*

class NewsDetailsFragment : Fragment() {

    private lateinit var newsItem: NewsItem
    private lateinit var newsTitle: TextView
    private lateinit var newsImage: ImageView
    private lateinit var newsDescription: TextView
    private lateinit var newsDetailsViewModel: NewsDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        newsItem = NewsItem()

        newsDetailsViewModel =
            ViewModelProvider(this@NewsDetailsFragment).get(NewsDetailsViewModel::class.java)

        val newsItem = arguments?.getParcelable<NewsItem>(ARG_NEWS_ITEM) as NewsItem
        newsDetailsViewModel.setSelectedNewsItem(newsItem)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_news_details, container, false)

        newsTitle = view.findViewById(R.id.news_title) as TextView
        newsImage = view.findViewById(R.id.news_image_details) as ImageView
        newsDescription = view.findViewById(R.id.news_description) as TextView

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newsDetailsViewModel.selectedNewsItem.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer { newsItem ->
                    this.newsItem = newsItem
                    updateUI()
            }
        )
    }

    private fun updateUI() {
        newsTitle.text = newsItem.title
        newsDescription.text = newsItem.description
        Picasso.get()
            .load(newsItem.urlToImage)
            .into(newsImage)
    }

    companion object {
        private const val ARG_NEWS_ITEM = "news_item"

        fun newInstance(newsItem: NewsItem) : NewsDetailsFragment {
            val args = Bundle().apply {
                putParcelable(ARG_NEWS_ITEM, newsItem)
            }
            return NewsDetailsFragment().apply {
                arguments = args
            }
        }
    }
}