package com.arifwidayana.challengechapter5.model.pojo.toprated


import com.google.gson.annotations.SerializedName

data class TopRatedMovie(
    @SerializedName("results")
    val results: List<ResultTopRated>,
)