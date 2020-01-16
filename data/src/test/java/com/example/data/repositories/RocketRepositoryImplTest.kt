package com.example.data.repositories

import com.example.data.api.RocketApi
import com.example.data.api.model.ApiEngine
import com.example.data.api.model.ApiRocket
import com.example.domain.model.ResultState
import com.example.domain.model.Rocket
import io.reactivex.Single
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class RocketRepositoryImplTest {

    private lateinit var rocketRepositoryImpl: RocketRepositoryImpl

    @Mock
    lateinit var rocketApi: RocketApi

    @BeforeEach
    fun beforeEach() {
        MockitoAnnotations.initMocks(this)
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        rocketRepositoryImpl = RocketRepositoryImpl(rocketApi)
    }

    @Test
    fun `Get rocket list When contditions are idle Return loading state and the success state`() {
        // Arrange
        val apiRocket = ApiRocket(
            name = "Vrocket",
            country = "Kazakhstan",
            imageList = listOf("this is a image list test"),
            engine = ApiEngine(1)
        )
        val fakeResult = Single.just(listOf(apiRocket))
        `when`(rocketApi.getRocketList()).thenReturn(fakeResult)


        //Act
        val testObservable = rocketRepositoryImpl.getRocketList().test()

        //Assert
        testObservable.assertValueCount(2)
        assert(testObservable.values()[0] is ResultState.Loading)
        assert(testObservable.values()[1] is ResultState.Success<List<Rocket>>)
    }

    @Test
    fun `Get rocket list When conditions are incorrect Return loading state and then failed state`() {
        // Arrange
        val fakeResult = Single.error<List<ApiRocket>>(Throwable())

        `when`(rocketApi.getRocketList()).thenReturn(fakeResult)


        //Act
        val testObservable = rocketRepositoryImpl.getRocketList().test()

        //Assert
        testObservable.assertValueCount(2)
        assert(testObservable.values()[0] is ResultState.Loading)
        assert(testObservable.values()[1] is ResultState.Error)
    }
}