package com.tashariko.exploredb.di.util

import androidx.work.CoroutineWorker
import dagger.android.AndroidInjector


object AndroidWorkerInjection {
    fun inject(worker: CoroutineWorker) {
        val application: Any = worker.applicationContext
        if (application !is HasWorkerInjector) {
            throw RuntimeException(
                String.format(
                    "%s does not implement %s",
                    application.javaClass.canonicalName,
                    HasWorkerInjector::class.java.canonicalName
                )
            )
        }
        val workerInjector: AndroidInjector<CoroutineWorker> = application.workerInjector()
        workerInjector.inject(worker)
    }
}