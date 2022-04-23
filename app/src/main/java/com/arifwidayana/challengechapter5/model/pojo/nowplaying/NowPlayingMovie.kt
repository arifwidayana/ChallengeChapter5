package com.arifwidayana.challengechapter5.model.pojo.nowplaying

import com.google.gson.annotations.SerializedName

data class NowPlayingMovie(
    @SerializedName("results")
    val results: List<ResultNowPlaying>
)