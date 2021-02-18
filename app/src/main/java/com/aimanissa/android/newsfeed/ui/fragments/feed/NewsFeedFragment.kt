package com.aimanissa.android.newsfeed.ui.fragments.feed

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.aimanissa.android.newsfeed.R
import com.aimanissa.android.newsfeed.data.app.model.NewsItem
import com.aimanissa.android.newsfeed.databinding.FragmentNewsFeedBinding
import com.aimanissa.android.newsfeed.di.components.NewsFeedFragmentSubcomponent
import com.aimanissa.android.newsfeed.ui.activity.MainActivity
import com.aimanissa.android.newsfeed.ui.fragments.feed.adapter.NewsAdapter
import com.google.android.material.snackbar.Snackbar

class NewsFeedFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private var binding: FragmentNewsFeedBinding? = null
    private lateinit var viewModel: NewsFeedViewModel
    private lateinit var component: NewsFeedFragmentSubcomponent
    private lateinit var newsAdapter: NewsAdapter
    private var searchQuery: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        component = (activity as MainActivity).mainActivityComponent().newsFeedComponent()
        viewModel = ViewModelProvider(this, component.viewModelFactory())
            .get(NewsFeedViewModel::class.java)
        lifecycle.addObserver(viewModel)
        viewModel.initViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsFeedBinding.inflate(inflater, container, false)

        (activity as MainActivity).setBackButton(false)

        newsAdapter = NewsAdapter()
        newsAdapter.onItemClickListener = { s: String ->
            (activity as MainActivity).openNewsDetailsFragment(s)
        }

        binding?.apply {
            swipeRefresh.apply {
                setOnRefreshListener(this@NewsFeedFragment)
                setColorSchemeResources(R.color.design_default_color_primary)
            }
            recyclerView.apply {
                LinearLayoutManager(context)
                adapter = newsAdapter
            }
            refreshButton.setOnClickListener {
                viewModel.loadNews()
            }
        }

        viewModel.apply {
            loadedNews.observe({ viewLifecycleOwner.lifecycle }, ::setItems)
            isNewsFound.observe({ viewLifecycleOwner.lifecycle }, ::setVisibility)
            isLoadError.observe({ viewLifecycleOwner.lifecycle }, ::showErrorMessage)
        }

        return binding!!.root
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    override fun onRefresh() {
        viewModel.onRefresh()
        binding?.swipeRefresh?.isRefreshing = false
    }

    private fun setItems(items: List<NewsItem>) {
        newsAdapter.updateNews(items)
    }

    private fun setVisibility(isNewsFound: Boolean) {
        binding?.apply {
            if (isNewsFound) {
                recyclerView.visibility = View.VISIBLE
                textNoResult.visibility = View.GONE
                refreshButton.visibility = View.GONE
            } else {
                recyclerView.visibility = View.GONE
                textNoResult.visibility = View.VISIBLE
                refreshButton.visibility = View.VISIBLE
            }
        }
    }

    private fun showErrorMessage(loadError: Boolean) {
        if (loadError) {
            view?.let {
                Snackbar.make(it, R.string.error_connection, Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.options_menu, menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        val searchItem: MenuItem = menu.findItem(R.id.menu_item_search)
        val searchView = searchItem.actionView as SearchView

        searchView.apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    Log.d(TAG, "onQueryTextSubmit: $query")
                    query?.let { viewModel.loadSearchNews(it) }
                    searchQuery = query

                    //hide keyboard after input
                    val inputManager =
                        context?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                    inputManager?.hideSoftInputFromWindow(this@apply.windowToken, 0)

                    (activity as MainActivity).apply {
                        setBackButton(true)
                        toolbar.setNavigationOnClickListener {
                            viewModel.loadNewsFromDb()
                            setBackButton(false)
                        }
                    }
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    Log.d(TAG, "onQueryTextChange: $newText")
                    return false
                }
            })

            setOnSearchClickListener {
                this.setQuery(searchQuery, false)
            }

            setOnCloseListener {
                searchQuery = null
                viewModel.loadNews()
                onActionViewCollapsed()
                true
            }
        }
    }

    companion object {
        private const val TAG = "NewsFeedFragment"

        fun newInstance() = NewsFeedFragment()
    }
}