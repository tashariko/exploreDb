package com.tashariko.exploredb.ui.main.detail

import android.os.Bundle
import android.util.Log
import com.tashariko.exploredb.application.base.BaseActivity
import com.tashariko.exploredb.databinding.ActivityItemDetailBinding

class ItemDetailActivity: BaseActivity() {

    lateinit var binding: ActivityItemDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun handleIncomingIntent() {
        TODO("Not yet implemented")
    }

    override fun bindAndSetupUI() {
        TODO("Not yet implemented")
    }

    override fun vmListeners() {
        TODO("Not yet implemented")
    }

    override fun viewlisteners() {
        TODO("Not yet implemented")
    }

}