package com.tashariko.exploredb.di.util

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * Kotlin extensions for dependency injection
 */

inline fun <reified VM : ViewModel> FragmentActivity.injectViewModel( factory: ViewModelProvider.Factory): VM {
    return ViewModelProvider(this, factory)[VM::class.java]
}

inline fun <reified VM : ViewModel> Fragment.injectViewModel(factory: ViewModelProvider.Factory): VM {
    return ViewModelProvider(this, factory)[VM::class.java]
}
