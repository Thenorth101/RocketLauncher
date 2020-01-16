package com.example.data.repositories

import com.example.data.api.RocketApi
import com.example.domain.model.ResultState
import com.example.domain.model.Rocket
import com.example.domain.repositories.RocketRepository
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class RocketRepositoryImpl(private val rocketApi: RocketApi) : RocketRepository {

    override fun getRocketList(): Observable<ResultState<List<Rocket>>> {
        return rocketApi.getRocketList().subscribeOn(Schedulers.io())
            .map<ResultState<List<Rocket>>> { apiRocketList ->
                val rocketList: List<Rocket> = apiRocketList.map { apiRocket ->
                    Rocket(
                        name = apiRocket.name,
                        country = apiRocket.country,
                        imageUrl = apiRocket.imageList.firstOrNull(),
                        numberOfEngines = apiRocket.engine.number
                    )
                }

                ResultState.Success(rocketList)
            }
            .onErrorReturn { Throwable ->
                ResultState.Error(Throwable)
            }
            .toObservable()
            .startWith(ResultState.Loading())
    }
}