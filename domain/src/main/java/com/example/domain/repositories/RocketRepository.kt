package com.example.domain.repositories

import com.example.domain.model.ResultState
import com.example.domain.model.Rocket
import io.reactivex.Observable

interface RocketRepository {
    fun getRocketList(): Observable<ResultState<List<Rocket>>>
}