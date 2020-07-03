package com.demo.tvshows.data

object Constants {
    const val PREFIX_IMAGE_URL = "https://image.tmdb.org/t/p/w500/"

    enum class ServiceEndpoints(val endpoint: String) {
        MOVIE_SERVICE_BASE_URL("https://api.themoviedb.org/3/")
    }
}
