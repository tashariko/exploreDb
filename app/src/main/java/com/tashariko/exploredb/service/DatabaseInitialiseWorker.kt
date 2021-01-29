package com.tashariko.exploredb.service

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class DatabaseInitialiseWorker constructor( context: Context, workerParams: WorkerParameters) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {

        return Result.success()
    }

}