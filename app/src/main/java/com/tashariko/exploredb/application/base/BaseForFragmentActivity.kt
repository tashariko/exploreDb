package com.tashariko.exploredb.application.base

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.commit
import com.tashariko.exploredb.R

class BaseForFragmentActivity : BaseActivity() {

    lateinit var fragType: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleIncomingIntent()

        setContentView(R.layout.activity_base_for_fragment)
        bindAndSetupUI()
    }

    override fun handleIncomingIntent() {
        fragType = intent.extras!!.getString(TYPE_FRAGMENT)!!
    }

    fun bindAndSetupUI() {
        val bundle = Bundle()
        supportFragmentManager.commit {
            when (fragType) {

            }
        }

    }

    fun vmListeners() {
        TODO("Not yet implemented")
    }

    fun viewlisteners() {
        TODO("Not yet implemented")
    }


    companion object {
        const val TYPE_FRAGMENT = "typeFragment"

    }

}