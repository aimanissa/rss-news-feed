package com.aimanissa.android.newsfeed.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.aimanissa.android.newsfeed.NewsApplication
import com.aimanissa.android.newsfeed.R
import com.aimanissa.android.newsfeed.databinding.ActivityMainBinding
import com.aimanissa.android.newsfeed.ui.fragments.details.NewsDetailsFragment
import com.aimanissa.android.newsfeed.ui.fragments.feed.NewsFeedFragment

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        toolbar = binding.toolBar
        setSupportActionBar(toolbar)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.fragment_container, NewsFeedFragment.newInstance())
                .commit()
        }
    }

    fun openNewsDetailsFragment(newsTitle: String) {
        val fragment = NewsDetailsFragment.newInstance(newsTitle)
        supportFragmentManager
            .beginTransaction()
            .setReorderingAllowed(true)
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(TAG_DETAILS_FRAGMENT)
            .commit()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val detailsFragment = supportFragmentManager.findFragmentByTag(TAG_DETAILS_FRAGMENT)
        if (detailsFragment != null) {
            supportFragmentManager.beginTransaction()
                .remove(detailsFragment)
                .commit()
        }
    }

    fun setBackButton(isAvailable: Boolean) {
        if (isAvailable) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            toolbar.navigationIcon = applicationContext.let {
                ContextCompat.getDrawable(it, R.drawable.ic_back_white_24)
            }
        } else {
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
        }
    }

    fun mainActivityComponent() = NewsApplication.appComponent.getMainActivitySubcomponent()

    companion object {
        private const val TAG_DETAILS_FRAGMENT = "NewsDetailsFragment"

        fun newIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }
}