package com.tashariko.exploredb.di.util

import androidx.work.CoroutineWorker
import dagger.android.AndroidInjector


interface HasWorkerInjector {
    fun workerInjector(): AndroidInjector<CoroutineWorker>
}