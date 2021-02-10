package com.aimanissa.android.newsfeed.ui.fragments.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.aimanissa.android.newsfeed.R
import com.aimanissa.android.newsfeed.adapter.NewsAdapter
import com.aimanissa.android.newsfeed.data.app.model.NewsItem
import com.aimanissa.android.newsfeed.databinding.FragmentNewsFeedBinding
import com.aimanissa.android.newsfeed.di.components.NewsFeedFragmentSubcomponent
import com.aimanissa.android.newsfeed.ui.activity.MainActivity
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

class NewsFeedFragment : MvpAppCompatFragment(), NewsFeedView, SwipeRefreshLayout.OnRefreshListener {

    @InjectPresenter
    lateinit var presenter: NewsFeedPresenter

    private var binding: FragmentNewsFeedBinding? = null
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var component: NewsFeedFragmentSubcomponent

    @ProvidePresenter
    fun providePresenter(): NewsFeedPresenter {
        return component.providePresenter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component = (activity as MainActivity).mainActivityComponent().newsFeedComponent()
        component.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsFeedBinding.inflate(inflater, container, false)

        (activity as MainActivity).apply {
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
        }

        newsAdapter = NewsAdapter()
        newsAdapter.onItemClickListener = { s: String ->
            (activity as MainActivity).openNewsDetailsFragment(s)
        }

        binding?.swipeRefresh?.apply {
            setOnRefreshListener(this@NewsFeedFragment)
            setColorSchemeResources(R.color.design_default_color_primary)
        }

        binding?.recyclerView?.apply {
            LinearLayoutManager(context)
            adapter = newsAdapter
        }

        return binding!!.root
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    override fun onRefresh() {
        presenter.onRefresh()
        binding?.swipeRefresh?.isRefreshing = false
    }

    override fun setItems(items: List<NewsItem>) {
        newsAdapter.updateNews(items)
    }

    companion object {
        fun newInstance() = NewsFeedFragment()
    }
}