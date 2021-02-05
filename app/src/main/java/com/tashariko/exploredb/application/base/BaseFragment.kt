package com.tashariko.exploredb.application.base

import android.content.Context
import android.view.View
import androidx.fragment.app.Fragment
import dagger.android.support.AndroidSupportInjection

open class BaseFragment : Fragment() {

    lateinit var mContext: Context
    protected lateinit var rootview: View

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
        mContext = context
    }


}