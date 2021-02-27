package com.tashariko.exploredb.ui.main

import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.tashariko.exploredb.R
import com.tashariko.exploredb.application.base.BaseActivity
import com.tashariko.exploredb.databinding.ActivityMainBinding
import com.tashariko.exploredb.ui.main.trending.TrendingFragment
import com.tashariko.exploredb.ui.main.user.UserFragment
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

        NetworkObserver.getNetLiveData(this).observe(this, Observer {
            binding.offlineContainerView.isVisible = it
        })
    }

    override fun viewlisteners() {

    }
}