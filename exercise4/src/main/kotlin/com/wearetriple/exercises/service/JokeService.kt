package com.wearetriple.exercises.service

import com.wearetriple.exercises.model.JokeResponse
import retrofit2.http.GET

interface JokeService {

    @GET("random_joke")
    suspend fun getJoke(): JokeResponse
}