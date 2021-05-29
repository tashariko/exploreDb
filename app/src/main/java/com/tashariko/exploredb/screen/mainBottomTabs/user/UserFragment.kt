package com.tashariko.exploredb.screen.mainBottomTabs.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tashariko.exploredb.application.base.BaseFragment
import com.tashariko.exploredb.databinding.FragmentUserBinding
import javax.inject.Inject

class UserFragment @Inject constructor() : BaseFragment() {

    lateinit var binding: FragmentUserBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserBinding.inflate(inflater, container, false)
        rootview = binding.root
        bindAndSetupUI()

        return rootview
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