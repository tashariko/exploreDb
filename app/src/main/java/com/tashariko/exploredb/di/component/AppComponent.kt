package com.tashariko.exploredb.di.component

import android.app.Application
import androidx.startup.Initializer
import com.tashariko.exploredb.application.MainApplication
import com.tashariko.exploredb.di.module.*
import javax.inject.Singleton


import dagger.BindsInstance

import dagger.Component

import dagger.android.AndroidInjectionModule

import dagger.android.AndroidInjector

import dagger.android.support.DaggerApplication


@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        DbModule::class,
        ActivityModule::class,
        FragmentModule::class,
        ApiServiceModule::class,
        ViewModelModule::class,
        WorkerModule::class,
        ServiceModule::class
    ]
)
interface AppComponent : AndroidInjector<DaggerApplication> {

    //binds items provided by @BindsInstance with dependencies and modules to create a new component for graph
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent

    }

    fun inject(application: MainApplication)

}