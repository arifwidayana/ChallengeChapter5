package com.arifwidayana.challengechapter5.model.pojo.upcoming

import com.google.gson.annotations.SerializedName

data class UpComingMovie(
    @SerializedName("results")
    val results: List<ResultUpComing>
)