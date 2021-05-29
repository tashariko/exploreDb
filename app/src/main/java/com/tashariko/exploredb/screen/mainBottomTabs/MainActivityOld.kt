package com.tashariko.exploredb.screen.mainBottomTabs

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.tashariko.exploredb.R
import com.tashariko.exploredb.application.AppConstants
import com.tashariko.exploredb.application.base.BaseActivity
import com.tashariko.exploredb.databinding.ActivityMainBinding
import com.tashariko.exploredb.screen.mainBottomTabs.trending.ui.TrendingFragment
import com.tashariko.exploredb.util.SharedPreferenceHelper
import javax.inject.Inject

class MainActivityOld : BaseActivity(), SetMainTitle {

    lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var trendingFragment: TrendingFragment

    companion object {
        fun launchSdcreen(context: Context) {
            context.startActivity(Intent(context, MainActivityOld::class.java))
            (context as BaseActivity).finish()
        }
    }

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

    fun bindAndSetupUI() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, trendingFragment, "CustomFragment.TAG")
            .commitAllowingStateLoss()
    }

    fun vmListeners() {
        binding.changeThemeView.setOnClickListener {
            SharedPreferenceHelper.putInSharedPreference(
                this,
                AppConstants.SP_IS_LIGHT_THEME_KEY,
                !SharedPreferenceHelper.getBooleanFromSharedPreference(
                    this,
                    AppConstants.SP_IS_LIGHT_THEME_KEY,
                    true
                )
            )
            val intent = intent
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
            finish()
        }
    }

    fun viewlisteners() {

    }

    override fun setTitle(title: String) {
        binding.titleView.text = title
    }

}


interface SetMainTitle {
    fun setTitle(title: String)
}
