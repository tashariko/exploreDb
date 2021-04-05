package com.tashariko.exploredb.application.base

import android.content.Context
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tashariko.exploredb.application.AppConstants
import com.tashariko.exploredb.network.ConfigurationResponse
import com.tashariko.exploredb.util.SharedPreferenceHelper

open class BaseViewModel: ViewModel() {



    fun getImagePath(mContext: Context): Triple<String, String, String> {
        val string = SharedPreferenceHelper.getStringFromSharedPreference(
                mContext,
                AppConstants.SP_KEY_CONFIG
        )
        val type = object : TypeToken<ConfigurationResponse>() {}.type
        val config: ConfigurationResponse = Gson().fromJson(string, type)

        with(config.images) {
            var original = ""
            var downed = ""
            for (item in this.poster_sizes) {
                if(item == "original"){
                    original = item
                }else if(item == "w154") {
                    downed = item
                }
            }

            return Triple(base_url,downed, original)
        }

    }

}