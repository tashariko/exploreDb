package com.tashariko.exploredb.di.module

import com.tashariko.exploredb.service.DatabaseInitialiseWorker
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class WorkerModule {
    @ContributesAndroidInjector
    abstract fun bindDownloadAudioWorker(): DatabaseInitialiseWorker
}
