package com.tashariko.exploredb.ui.main

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import com.tashariko.exploredb.R
import com.tashariko.exploredb.application.base.BaseActivity
import com.tashariko.exploredb.databinding.ActivityMainBinding
import com.tashariko.exploredb.ui.main.trending.ui.TrendingFragment
import com.tashariko.exploredb.util.NetworkObserver
import javax.inject.Inject

class MainActivity : BaseActivity() {

    lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var trendingFragment: TrendingFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        handleIncomingIntent()
        bindAndSetupUI()
        vmListeners()
        viewlisteners()
    }

    override fun handleIncomingIntent() {

    }

    override fun bindAndSetupUI() {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainer, trendingFragment, "CustomFragment.TAG")
                .commitAllowingStateLoss()
    }

    override fun vmListeners() {
        binding.changeThemeView.setOnClickListener {

        }
    }

    override fun viewlisteners() {

    }
}