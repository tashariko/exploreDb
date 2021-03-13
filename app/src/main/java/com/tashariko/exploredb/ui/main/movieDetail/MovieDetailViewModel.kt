package com.tashariko.exploredb.ui.main.movieDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tashariko.exploredb.database.entity.Movie
import com.tashariko.exploredb.network.result.ApiResult
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
}