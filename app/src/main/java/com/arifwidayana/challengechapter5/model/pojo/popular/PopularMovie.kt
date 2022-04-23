package com.arifwidayana.challengechapter5.model.pojo.popular


import com.google.gson.annotations.SerializedName

data class PopularMovie(
    @SerializedName("results")
    val results: List<ResultPopular>
)