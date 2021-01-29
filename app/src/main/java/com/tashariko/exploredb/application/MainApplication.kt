package com.tashariko.exploredb.application

import android.app.Activity
import android.app.Service
import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.multidex.MultiDexApplication
import androidx.work.Configuration
import androidx.work.CoroutineWorker
import androidx.work.Worker
import com.facebook.stetho.Stetho
import com.tashariko.exploredb.di.injectable.AppInjector
import com.tashariko.exploredb.di.util.HasWorkerInjector
import com.tashariko.exploredb.util.NetworkObserver
import com.tashariko.exploredb.util.SingleLiveEvent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.HasServiceInjector
import timber.log.Timber
import javax.inject.Inject

class MainApplication: MultiDexApplication(), HasActivityInjector, HasServiceInjector,
    HasWorkerInjector {

    @Inject
    lateinit var workerDispatchingAndroidInjector: DispatchingAndroidInjector<Worker>

    @Inject
    lateinit var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var dispatchingServiceInjector: DispatchingAndroidInjector<Service>


    @Inject
    lateinit var androidWorkerInjector: DispatchingAndroidInjector<CoroutineWorker>



    override fun onCreate() {
        super.onCreate()
        /**
         * Not doing here as done in manifest
         */
//        AppInitializer.getInstance(this).initializeComponent(TimberInitializer::class.java)
//        AppInitializer.getInstance(this).initializeComponent(StethoInitializer::class.java)

        //Will happen after Initializer intialiases itself
        Timber.i("Application Initialized")

        AppInjector.init(this)
    }

    public fun setNetObserver(owner: LifecycleOwner): SingleLiveEvent<Boolean> {

        var netListener = SingleLiveEvent<Boolean>()

        NetworkObserver.getNetLiveData(this).observe(owner, androidx.lifecycle.Observer {
            netListener.postValue(it)
        })

        return netListener
    }

    override fun serviceInjector(): AndroidInjector<Service> = dispatchingServiceInjector

    override fun activityInjector(): AndroidInjector<Activity> = activityDispatchingAndroidInjector

    override fun workerInjector(): AndroidInjector<CoroutineWorker> = androidWorkerInjector

}