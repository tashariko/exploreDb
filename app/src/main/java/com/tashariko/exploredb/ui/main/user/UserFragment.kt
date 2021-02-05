package com.tashariko.exploredb.ui.main.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.tashariko.exploredb.application.base.BaseActivity
import com.tashariko.exploredb.application.base.BaseFragment
import com.tashariko.exploredb.databinding.FragmentUserBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserFragment @Inject constructor():  BaseFragment() {

    lateinit var binding: FragmentUserBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentUserBinding.inflate(inflater, container, false)
        rootview = binding.root
        bindAndSetupUI()

        return rootview
    }

    private fun bindAndSetupUI() {

    }

}