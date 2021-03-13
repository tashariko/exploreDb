package com.tashariko.exploredb.screen.main.movieDetail

import com.tashariko.exploredb.application.base.BaseDataSource
import com.tashariko.exploredb.database.dao.MovieDao
import com.tashariko.exploredb.database.entity.Movie
import com.tashariko.exploredb.network.NetworkBoundRepository
import com.tashariko.exploredb.network.apiservices.MiscApiService
import com.tashariko.exploredb.network.result.ApiResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

class ItemDetailRepository @Inject constructor(val movieDao: MovieDao, private val itemDetailRemoteDataSource: ItemDetailRemoteDataSource) {

    @ExperimentalCoroutinesApi
    fun getMovie(id: Long) = object : NetworkBoundRepository<Movie, Movie>() {

    }.flowData(
            databaseQuery = {
                movieDao.getMovie(id)
            },
            networkCall = {
                itemDetailRemoteDataSource.getMovieDetail(id)
            },
            saveCallResult = {
                movieDao.insert(it)
            },
            parseNetworkResponse = {
                ApiResult.success(it)
            }
    )
}

class ItemDetailRemoteDataSource @Inject constructor(private val miscApiService: MiscApiService) : BaseDataSource() {

    suspend fun getMovieDetail(id: Long)  = getResult {
        miscApiService.getMovieDetail(id)
    }
}