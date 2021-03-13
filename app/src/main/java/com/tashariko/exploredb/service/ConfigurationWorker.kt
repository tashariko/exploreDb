package com.tashariko.exploredb.service

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import com.tashariko.exploredb.application.AppConstants
import com.tashariko.exploredb.di.util.AndroidWorkerInjection
import com.tashariko.exploredb.network.result.ApiResult
import com.tashariko.exploredb.screen.splash.LandingRepository
import com.tashariko.exploredb.util.SharedPreferenceHelper
import kotlinx.coroutines.flow.*
import timber.log.Timber
import javax.inject.Inject

class ConfigurationWorker constructor(context: Context, workerParams: WorkerParameters) : CoroutineWorker(context, workerParams) {

    val MAX_COUNT = 3

    @Inject
    lateinit var repository: LandingRepository

    var count = 0

    init {
        AndroidWorkerInjection.inject(this)
    }

    override suspend fun doWork(): Result {
        Timber.i("HAHA__started")

        if(count <= MAX_COUNT) {
            count += 1
        }else{
            return Result.failure()
        }

        val status = repository.getData().first {
            it.status == ApiResult.Status.SUCCESS || it.status == ApiResult.Status.ERROR
        }

        Timber.i("HAHA__done")
        return if(status.status == ApiResult.Status.SUCCESS){
            Timber.i("HAHA__success")
            val gson = Gson()
            val type = object : TypeToken<JsonObject>() {}.type
            val configString =  gson.toJson(status.data!!, type)
            SharedPreferenceHelper.putInSharedPreference(applicationContext, AppConstants.SP_KEY_CONFIG, configString)
            Result.success()

        }else{
            Timber.i("HAHA__retry")
            Result.retry()
        }
    }
}