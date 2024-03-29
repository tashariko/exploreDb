package com.tashariko.exploredb.application.base

import android.content.Context
import android.view.View
import androidx.fragment.app.Fragment

open abstract class BaseFragment : Fragment() {

    lateinit var mContext: Context
    protected lateinit var rootview: View

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }


    abstract fun handleIncomingIntent()
    abstract fun bindAndSetupUI()
    abstract fun vmListeners()
    abstract fun viewlisteners()


}