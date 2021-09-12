package com.tashariko.exploredb.screen.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.tashariko.exploredb.R
import com.tashariko.exploredb.application.AppConstants
import com.tashariko.exploredb.application.base.BaseActivity
import com.tashariko.exploredb.databinding.ActivityOldMainBinding
import com.tashariko.exploredb.screen.home.trending.ui.TrendingFragment
import com.tashariko.exploredb.util.SharedPreferenceHelper
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OldMainActivity : BaseActivity(){

    lateinit var binding: ActivityOldMainBinding

//    @Inject
//    lateinit var trendingFragment: TrendingFragment

    companion object {
        fun launchScreen(context: Context) {
            context.startActivity(Intent(context, OldMainActivity::class.java))
            (context as BaseActivity).finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOldMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        handleIncomingIntent()
        bindAndSetupUI()
        vmListeners()
        viewlisteners()
    }


    override fun handleIncomingIntent() {

    }

    private fun bindAndSetupUI() {
//        supportFragmentManager
//            .beginTransaction()
//            .replace(R.id.fragmentContainer, trendingFragment, "CustomFragment.TAG")
//            .commitAllowingStateLoss()
    }

    private fun vmListeners() {
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

    private fun viewlisteners() {

    }

}