package com.tashariko.exploredb.di.injectable

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.tashariko.exploredb.application.MainApplication
import com.tashariko.exploredb.di.component.DaggerAppComponent

import timber.log.Timber

object AppInjector {

    fun init(application: MainApplication){
        DaggerAppComponent.builder().application(application).build().inject(application)

    }

}