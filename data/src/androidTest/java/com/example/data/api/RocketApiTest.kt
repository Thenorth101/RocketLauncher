package com.example.data.api

import com.example.data.api.model.ApiRocket
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.AssertionError

class RocketApiTest {

    private lateinit var rocketApi: RocketApi

    @Before
    fun before() {

        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val retrofit = Retrofit.Builder().apply {
            baseUrl("https://api.spacexdata.com/")
            addConverterFactory(GsonConverterFactory.create(Gson()))
            addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            client(
                OkHttpClient.Builder().apply {
                    addInterceptor(httpLoggingInterceptor)
                }.build()
            )
        }.build()

        rocketApi = retrofit.create(RocketApi::class.java)
    }

    @Test
    fun getRocketList_WhenConditionsAreIdle_ReturnApiRocketList() {
        //Arrange

        //Act
        val testObservable = rocketApi.getRocketList().test()

        //Assert
        testObservable.assertValueCount(1)
        assert(testObservable.values()[0] is List<ApiRocket>)
        val apiRocketList = testObservable.values()[0] as List<ApiRocket>
        apiRocketList.forEach { ApiRocket ->
            Assert.assertNotNull(ApiRocket.name)
            Assert.assertNotNull(ApiRocket.country)
            Assert.assertNotNull(ApiRocket.engine)
            Assert.assertNotNull(ApiRocket.imageList)
            Assert.assertNotNull(ApiRocket.engine.number)
        }
    }
}