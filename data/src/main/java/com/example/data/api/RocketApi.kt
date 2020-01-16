package com.example.data.api

import com.example.data.api.model.ApiRocket
import io.reactivex.Single
import retrofit2.http.GET

interface RocketApi {

    @GET("/v3/rockets")
    fun getRocketList():Single<List<ApiRocket>>
}