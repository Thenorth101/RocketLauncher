package com.example.domain.repositories

import com.example.domain.entities.ResultState
import com.example.domain.entities.Rocket
import io.reactivex.Observable

interface RocketRepository {
    fun getRocketList(): Observable<ResultState<List<Rocket>>>
}