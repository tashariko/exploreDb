package com.tashariko.exploredb.di.injectable

import com.tashariko.exploredb.application.MainApplication
import com.tashariko.exploredb.di.component.DaggerAppComponent

object AppInjector {

    fun init(application: MainApplication) {
        DaggerAppComponent.builder().application(application).build().inject(application)
    }

}