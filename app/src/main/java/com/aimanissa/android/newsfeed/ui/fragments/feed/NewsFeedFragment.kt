package com.aimanissa.android.newsfeed.ui.fragments.feed


import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.work.*
import com.aimanissa.android.newsfeed.R
import com.aimanissa.android.newsfeed.data.app.model.NewsItem
import com.aimanissa.android.newsfeed.databinding.FragmentNewsFeedBinding
import com.aimanissa.android.newsfeed.di.components.NewsFeedFragmentSubcomponent
import com.aimanissa.android.newsfeed.ui.activity.MainActivity
import com.aimanissa.android.newsfeed.ui.fragments.feed.adapter.NewsAdapter
import com.aimanissa.android.newsfeed.ui.fragments.feed.service.UpdateWorker
import com.google.android.material.snackbar.Snackbar
import java.util.concurrent.TimeUnit

class NewsFeedFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private var binding: FragmentNewsFeedBinding? = null
    private lateinit var viewModel: NewsFeedViewModel
    private lateinit var component: NewsFeedFragmentSubcomponent
    private lateinit var newsAdapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        component = (activity as MainActivity).mainActivityComponent().newsFeedComponent()
        viewModel = ViewModelProvider(this, component.viewModelFactory())
            .get(NewsFeedViewModel::class.java)
        lifecycle.addObserver(viewModel)
        viewModel.initViewModel()

        val workRequest =
            PeriodicWorkRequestBuilder<UpdateWorker>(30, TimeUnit.MINUTES)
                .setBackoffCriteria(BackoffPolicy.LINEAR,10, TimeUnit.SECONDS).build()
        WorkManager.getInstance(requireContext())
            .enqueueUniquePeriodicWork(
                "update_worker",
                ExistingPeriodicWorkPolicy.REPLACE,
                workRequest
            )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsFeedBinding.inflate(inflater, container, false)

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
            (activity as MainActivity).apply {
                if (isNewsFound) {
                    recyclerView.visibility = View.VISIBLE
                    textNoResult.visibility = View.GONE
                    refreshButton.visibility = View.GONE
                    setBackButton(false)
                } else {
                    recyclerView.visibility = View.GONE
                    textNoResult.visibility = View.VISIBLE
                    refreshButton.visibility = View.VISIBLE
                    setBackButton(true)
                    toolbar.setNavigationOnClickListener {
                        viewModel.loadNewsFromDb()
                        setBackButton(false)
                    }
                }
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

                    //save query
                    PreferenceManager.getDefaultSharedPreferences(context)
                        .edit { putString(PREF_SEARCH_QUERY, query) }

                    //hide keyboard after input
                    val inputManager =
                        context?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                    inputManager?.hideSoftInputFromWindow(this@apply.windowToken, 0)
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    Log.d(TAG, "onQueryTextChange: $newText")
                    return false
                }
            })

            setOnSearchClickListener {
                val query = PreferenceManager.getDefaultSharedPreferences(context)
                    .getString(PREF_SEARCH_QUERY, "")
                this.setQuery(query, false)
            }

            setOnCloseListener {
                viewModel.loadNews()
                PreferenceManager.getDefaultSharedPreferences(context).edit { clear() }
                onActionViewCollapsed()
                true
            }
        }
    }

    companion object {
        private const val TAG = "NewsFeedFragment"
        const val PREF_SEARCH_QUERY = "searchQuery"

        fun newInstance() = NewsFeedFragment()
    }
}