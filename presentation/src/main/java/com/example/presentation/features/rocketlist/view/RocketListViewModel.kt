package com.example.presentation.features.rocketlist.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.model.ResultState
import com.example.domain.repositories.RocketRepository
import com.example.presentation.R
import com.example.presentation.features.rocketlist.model.UiRocket
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class RocketListViewModel(private val rocketRepository: RocketRepository) : ViewModel() {

    private var compositeDisposable = CompositeDisposable()
    private val loading: MutableLiveData<Boolean> = MutableLiveData()
    private val error: MutableLiveData<Int> = MutableLiveData()
    private val result: MutableLiveData<List<UiRocket>> = MutableLiveData()

    fun getLoading(): LiveData<Boolean>{
       return loading
    }

    fun getError(): LiveData<Int>{
        return error
    }

    fun getResult(): LiveData<List<UiRocket>>{
        return result
    }

    fun getRocketList() {
        val disposable = rocketRepository.getRocketList().observeOn(AndroidSchedulers.mainThread())
            .subscribe { resultState ->
                when (resultState) {
                    is ResultState.Loading -> loading.value = true
                    is ResultState.Success -> {
                        loading.value = false
                        val uiRocketList: List<UiRocket> = resultState.data.map { rocket ->
                            UiRocket(
                                name = rocket.name,
                                imageUrl = rocket.imageUrl,
                                country = rocket.country,
                                numberOfEngines = rocket.numberOfEngines
                            )
                        }
                        result.value = uiRocketList
                    }
                    is ResultState.Error -> {
                        loading.value = false
                        error.value = R.string.error_generic
                    }
                }
            }

        compositeDisposable.clear()
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    private fun dismissError(){
        error.value = null
    }
}