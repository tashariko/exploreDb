package com.tashariko.exploredb.screen.main.movieDetail

import androidx.lifecycle.viewModelScope
import com.tashariko.exploredb.application.base.BaseViewModel
import com.tashariko.exploredb.database.entity.Movie
import com.tashariko.exploredb.network.result.ApiResult
import com.tashariko.exploredb.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor() : BaseViewModel() {

    @Inject
    lateinit var repository: ItemDetailRepository

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