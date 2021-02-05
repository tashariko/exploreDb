package com.tashariko.exploredb.service

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.tashariko.exploredb.di.util.AndroidWorkerInjection
import kotlinx.coroutines.*

class DatabaseInitialiseWorker constructor( context: Context, workerParams: WorkerParameters) : CoroutineWorker(context, workerParams) {

    init {
        AndroidWorkerInjection.inject(this)
    }

    @InternalCoroutinesApi
    override suspend fun doWork(): Result = coroutineScope {

        Result.success()
    }

}