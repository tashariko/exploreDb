package com.tashariko.exploredb.screen.main.movieDetail

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tashariko.exploredb.application.AppConstants
import com.tashariko.exploredb.database.entity.Movie
import com.tashariko.exploredb.network.ConfigurationResponse
import com.tashariko.exploredb.network.result.ApiResult
import com.tashariko.exploredb.util.SharedPreferenceHelper
import com.tashariko.exploredb.util.SingleLiveEvent
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class MovieDetailViewModel  @Inject constructor(): ViewModel() {

    @Inject
    public lateinit var repository: ItemDetailRepository

    /**
     * Find a better way for this
     */
    private val _tempMovieDetailLiveData: SingleLiveEvent<ApiResult<Movie>> = SingleLiveEvent()
    val movieDetailLiveData: SingleLiveEvent<ApiResult<Movie>>
        get() = _tempMovieDetailLiveData

    fun getMovieDetail(id: Long) {
        viewModelScope.launch {
            repository.getMovie(id).collect {
                _tempMovieDetailLiveData.postValue(it)
            }
        }
    }


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