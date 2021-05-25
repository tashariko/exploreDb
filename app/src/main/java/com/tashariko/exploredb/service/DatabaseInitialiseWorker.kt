package com.tashariko.exploredb.service

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.*

class DatabaseInitialiseWorker constructor( context: Context, workerParams: WorkerParameters) : CoroutineWorker(context, workerParams) {

    @InternalCoroutinesApi
    override suspend fun doWork(): Result = coroutineScope {

        Result.success()
    }

}