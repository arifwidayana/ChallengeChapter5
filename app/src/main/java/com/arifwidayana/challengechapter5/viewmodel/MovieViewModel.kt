package com.arifwidayana.challengechapter5.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.arifwidayana.challengechapter5.model.pojo.details.DetailsMovie
import com.arifwidayana.challengechapter5.model.pojo.nowplaying.NowPlayingMovie
import com.arifwidayana.challengechapter5.model.pojo.popular.PopularMovie
import com.arifwidayana.challengechapter5.model.pojo.toprated.TopRatedMovie
import com.arifwidayana.challengechapter5.model.pojo.upcoming.UpComingMovie
import com.arifwidayana.challengechapter5.model.service.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieViewModel: ViewModel() {
    val status: MutableLiveData<String> = MutableLiveData()
    val loading: MutableLiveData<Boolean> = MutableLiveData()

    private val moviesNowPlaying: MutableLiveData<NowPlayingMovie> by lazy {
        MutableLiveData<NowPlayingMovie>().also {
            nowPlayingMovies()
        }
    }

    private val moviesPopular: MutableLiveData<PopularMovie> by lazy {
        MutableLiveData<PopularMovie>().also {
            popularMovies()
        }
    }

    private val moviesUpComing: MutableLiveData<UpComingMovie> by lazy {
        MutableLiveData<UpComingMovie>().also {
            upComingMovies()
        }
    }

    private val moviesTopRated: MutableLiveData<TopRatedMovie> by lazy {
        MutableLiveData<TopRatedMovie>().also {
            topRatedMovies()
        }
    }

    private val moviesDetail: MutableLiveData<DetailsMovie> = MutableLiveData()

    val dataNowPlayingMovies: LiveData<NowPlayingMovie> = moviesNowPlaying
    val dataPopularMovies: LiveData<PopularMovie> = moviesPopular
    val dataUpComingMovies: LiveData<UpComingMovie> = moviesUpComing
    val dataTopRatedMovies: LiveData<TopRatedMovie> = moviesTopRated
    val dataDetailMovies: LiveData<DetailsMovie> = moviesDetail

    private fun nowPlayingMovies() {
        loading.postValue(true)
        ApiClient.instance.getNowPlayingMovies().enqueue(object : Callback<NowPlayingMovie>{
            override fun onResponse(
                call: Call<NowPlayingMovie>,
                response: Response<NowPlayingMovie>
            ) {
                loading.postValue(false)
                when {
                    response.code() == 200 -> moviesNowPlaying.postValue(response.body())
                    else -> status.postValue("Error")
                }
            }

            override fun onFailure(call: Call<NowPlayingMovie>, t: Throwable) {
                loading.postValue(false)
            }
        })
    }

    private fun popularMovies() {
        loading.postValue(true)
        ApiClient.instance.getPopularMovies().enqueue(object : Callback<PopularMovie>{
            override fun onResponse(call: Call<PopularMovie>, response: Response<PopularMovie>) {
                loading.postValue(false)
                when {
                    response.code() == 200 -> moviesPopular.postValue(response.body())
                    else -> status.postValue("Error")
                }
            }

            override fun onFailure(call: Call<PopularMovie>, t: Throwable) {
                loading.postValue(false)
            }
        })
    }

    private fun upComingMovies() {
        loading.postValue(true)
        ApiClient.instance.getUpComingMovies().enqueue(object : Callback<UpComingMovie>{
            override fun onResponse(call: Call<UpComingMovie>, response: Response<UpComingMovie>) {
                loading.postValue(false)
                when {
                    response.code() == 200 -> moviesUpComing.postValue(response.body())
                    else -> status.postValue("Error")
                }
            }

            override fun onFailure(call: Call<UpComingMovie>, t: Throwable) {
                loading.postValue(false)
            }
        })
    }

    private fun topRatedMovies() {
        loading.postValue(true)
        ApiClient.instance.getTopRatedMovies().enqueue(object : Callback<TopRatedMovie>{
            override fun onResponse(call: Call<TopRatedMovie>, response: Response<TopRatedMovie>) {
                loading.postValue(false)
                when {
                    response.code() == 200 -> moviesTopRated.postValue(response.body())
                    else -> status.postValue("Error")
                }
            }

            override fun onFailure(call: Call<TopRatedMovie>, t: Throwable) {
                loading.postValue(false)
            }
        })
    }

    fun detailMovies(id: Int?) {
        loading.postValue(true)
        ApiClient.instance.getDetailMovies(id).enqueue(object : Callback<DetailsMovie>{
            override fun onResponse(call: Call<DetailsMovie>, response: Response<DetailsMovie>) {
                loading.postValue(false)
                when {
                    response.code() == 200 -> moviesDetail.postValue(response.body())
                    else -> status.postValue("Error")
                }
            }

            override fun onFailure(call: Call<DetailsMovie>, t: Throwable) {
                loading.postValue(false)
            }
        })
    }
}