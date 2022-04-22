package com.arifwidayana.challengechapter5.model.service

import com.arifwidayana.challengechapter5.model.pojo.details.DetailsMovie
import com.arifwidayana.challengechapter5.model.pojo.nowplaying.NowPlayingMovie
import com.arifwidayana.challengechapter5.model.pojo.popular.PopularMovie
import com.arifwidayana.challengechapter5.model.pojo.toprated.TopRatedMovie
import com.arifwidayana.challengechapter5.model.pojo.upcoming.UpComingMovie
import com.arifwidayana.challengechapter5.model.utils.Constant
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET(Constant.NOW_PLAYING)
    fun getNowPlayingMovies(): Call<NowPlayingMovie>

    @GET(Constant.POPULAR)
    fun getPopularMovies(): Call<PopularMovie>

    @GET(Constant.UP_COMING)
    fun getUpComingMovies(): Call<UpComingMovie>

    @GET(Constant.TOP_RATED)
    fun getTopRatedMovies(): Call<TopRatedMovie>

    @GET(Constant.DETAIL_MOVIES)
    fun getDetailMovies(@Path("id") id: Int?): Call<DetailsMovie>
}