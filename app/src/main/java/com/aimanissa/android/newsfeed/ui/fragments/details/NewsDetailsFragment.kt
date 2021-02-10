package com.aimanissa.android.newsfeed.ui.fragments.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.aimanissa.android.newsfeed.R
import com.aimanissa.android.newsfeed.data.app.model.NewsItem
import com.aimanissa.android.newsfeed.databinding.FragmentNewsDetailsBinding
import com.aimanissa.android.newsfeed.di.components.NewsDetailsFragmentSubcomponent
import com.aimanissa.android.newsfeed.ui.activity.MainActivity
import com.squareup.picasso.Picasso
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

class NewsDetailsFragment : MvpAppCompatFragment(), NewsDetailsView {

    @InjectPresenter
    lateinit var presenter: NewsDetailsPresenter

    private var binding: FragmentNewsDetailsBinding? = null
    private var newsItem: NewsItem? = null
    private lateinit var component: NewsDetailsFragmentSubcomponent

    @ProvidePresenter
    fun providePresenter(): NewsDetailsPresenter {
        return component.providePresenter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component = (activity as MainActivity).mainActivityComponent().newsDetailsComponent()
        component.inject(this)
        super.onCreate(savedInstanceState)

        val selectedNewsTitle = arguments?.getString(ARG_NEWS_TITLE)
        selectedNewsTitle?.let { presenter.selectedNewsTitle = it }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsDetailsBinding.inflate(inflater, container, false)

        (activity as MainActivity).apply {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            toolbar.navigationIcon = context?.let {
                ContextCompat.getDrawable(it, R.drawable.ic_back_white_24)
            }
            toolbar.setNavigationOnClickListener {
                this.onBackPressed()
            }
        }

        return binding!!.root
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    override fun setItem(item: NewsItem) {
        this.newsItem = item
    }

    override fun updateUI() {
        binding?.apply {
            newsTitle.text = newsItem?.title
            newsDescription.text = newsItem?.description
            Picasso.get()
                .load(newsItem?.urlToImage)
                .into(binding?.newsImageDetails)
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