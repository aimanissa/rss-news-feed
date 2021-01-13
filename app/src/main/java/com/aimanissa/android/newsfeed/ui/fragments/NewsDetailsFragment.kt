package com.aimanissa.android.newsfeed.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.aimanissa.android.newsfeed.data.app.model.NewsItem
import com.aimanissa.android.newsfeed.databinding.FragmentNewsDetailsBinding
import com.squareup.picasso.Picasso

class NewsDetailsFragment : Fragment() {

    private var fragmentNewsDetailsBinding: FragmentNewsDetailsBinding? = null
    private lateinit var newsItem: NewsItem
    private lateinit var viewModel: NewsDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentNewsDetailsBinding.inflate(inflater, container, false)
        fragmentNewsDetailsBinding = binding
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newsItem = NewsItem()
        viewModel = ViewModelProvider(this).get(NewsDetailsViewModel::class.java)
        setSelectedNews()
        observeNewsItem()
    }

    override fun onDestroyView() {
        fragmentNewsDetailsBinding = null
        super.onDestroyView()
    }

    private fun observeNewsItem() {
        viewModel.selectedNewsItem.observe(
            viewLifecycleOwner,
            { newsItem ->
                this.newsItem = newsItem
                updateUI()
            }
        )
    }

    private fun setSelectedNews() {
        val selectedNewsItem = arguments?.getParcelable<NewsItem>(ARG_NEWS_ITEM) as NewsItem
        viewModel.setSelectedNewsItem(selectedNewsItem)
    }

    private fun updateUI() {
        fragmentNewsDetailsBinding?.apply {
            newsTitle.text = newsItem.title
            newsDescription.text = newsItem.description
            Picasso.get()
                .load(newsItem.urlToImage)
                .into(fragmentNewsDetailsBinding?.newsImageDetails)
        }
    }

    companion object {
        private const val ARG_NEWS_ITEM = "news_item"

        fun newInstance(newsItem: NewsItem): NewsDetailsFragment {
            val args = Bundle().apply {
                putParcelable(ARG_NEWS_ITEM, newsItem)
            }
            return NewsDetailsFragment().apply {
                arguments = args
            }
        }
    }
}