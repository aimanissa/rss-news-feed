package com.aimanissa.android.newsfeed.ui.fragments.details

import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.aimanissa.android.newsfeed.R
import com.aimanissa.android.newsfeed.data.app.model.NewsItem
import com.aimanissa.android.newsfeed.databinding.FragmentNewsDetailsBinding
import com.aimanissa.android.newsfeed.di.components.NewsDetailsFragmentSubcomponent
import com.aimanissa.android.newsfeed.ui.activity.MainActivity
import com.squareup.picasso.Picasso

class NewsDetailsFragment : Fragment() {

    private var binding: FragmentNewsDetailsBinding? = null
    private var newsItem: NewsItem? = null
    private lateinit var viewModel: NewsDetailsViewModel
    private lateinit var component: NewsDetailsFragmentSubcomponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        component = (activity as MainActivity).mainActivityComponent().newsDetailsComponent()
        viewModel = ViewModelProvider(this, component.viewModelFactory())
            .get(NewsDetailsViewModel::class.java)

        val selectedNewsTitle = arguments?.getString(ARG_NEWS_TITLE)
        selectedNewsTitle?.let { viewModel.setSelectedNewsTitle(it) }

        lifecycle.addObserver(viewModel)
        viewModel.initViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsDetailsBinding.inflate(inflater, container, false)

        (activity as MainActivity).apply {
            setBackButton(true)
            toolbar.setNavigationOnClickListener {
                this.onBackPressed()
            }
        }

        viewModel.apply {
            loadedNewsItem.observe({ viewLifecycleOwner.lifecycle }, ::setItem)
        }

        return binding!!.root
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    private fun setItem(newsItem: NewsItem?) {
        this.newsItem = newsItem
        updateUI()
    }

    private fun updateUI() {
        binding?.apply {
            title.text = newsItem?.title
            description.text = newsItem?.description
            date.text = DateFormat.format("dd.MM.yyyy HH:mm", newsItem?.publishedAt)
            if (newsItem?.urlToImage.isNullOrEmpty()) {
                binding?.newsImageView?.setImageResource(R.drawable.ic_image_placeholder)
            } else {
                Picasso.get()
                    .load(newsItem?.urlToImage)
                    .into(binding?.newsImageView)
            }
        }
    }

    companion object {
        private const val ARG_NEWS_TITLE = "news_title"

        fun newInstance(newsTitle: String): NewsDetailsFragment {
            val args = Bundle().apply {
                putString(ARG_NEWS_TITLE, newsTitle)
            }
            return NewsDetailsFragment().apply {
                arguments = args
            }
        }
    }
}