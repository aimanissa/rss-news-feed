package com.aimanissa.android.newsfeed.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aimanissa.android.newsfeed.data.app.model.NewsItem
import com.aimanissa.android.newsfeed.R
import com.aimanissa.android.newsfeed.adapter.NewsAdapter
import com.aimanissa.android.newsfeed.databinding.ActivityMainBinding
import com.aimanissa.android.newsfeed.ui.fragments.NewsDetailsFragment
import com.aimanissa.android.newsfeed.ui.fragments.NewsFeedFragment

class MainActivity : AppCompatActivity(), NewsAdapter.OnItemClickListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val isFragmentContainerEmpty = savedInstanceState == null
        if (isFragmentContainerEmpty) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, NewsFeedFragment.newInstance())
                .commit()
        }
    }

    override fun onItemClick(newsItem: NewsItem) {
        val fragment = NewsDetailsFragment.newInstance(newsItem)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}