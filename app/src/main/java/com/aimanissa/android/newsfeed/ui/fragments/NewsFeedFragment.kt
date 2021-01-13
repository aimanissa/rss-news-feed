package com.aimanissa.android.newsfeed.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.aimanissa.android.newsfeed.adapter.NewsAdapter
import com.aimanissa.android.newsfeed.databinding.FragmentNewsFeedBinding
import com.aimanissa.android.newsfeed.di.DaggerAppComponent
import javax.inject.Inject

class NewsFeedFragment : Fragment() {

    @Inject
    lateinit var newsAdapter: NewsAdapter

    private var fragmentNewsFeedBinding: FragmentNewsFeedBinding? = null
    private lateinit var viewModel: NewsFeedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerAppComponent.create().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentNewsFeedBinding.inflate(inflater, container, false)
        fragmentNewsFeedBinding = binding
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(NewsFeedViewModel::class.java)
        setUpRecyclerView()
        observeNews()
    }

    override fun onDestroyView() {
        fragmentNewsFeedBinding = null
        super.onDestroyView()
    }

    private fun setUpRecyclerView() {
        fragmentNewsFeedBinding?.recyclerView?.apply {
            setHasFixedSize(true)
            LinearLayoutManager(context)
            adapter = newsAdapter
        }
    }

    private fun observeNews() {
        viewModel.newsLoader.newsList.observe(
            viewLifecycleOwner,
            Observer {
                newsAdapter.updateNews(it)
            })
    }

    companion object {
        fun newInstance() = NewsFeedFragment()
    }
}