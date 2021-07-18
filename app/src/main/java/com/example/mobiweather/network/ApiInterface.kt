package com.example.mobiweather.network

import com.example.mobiweather.models.WeatherData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("weather")
    fun callApiForWeatherInfo(@Query("lat") latitude: Double,
                               @Query("lon") longitude: Double,
                              @Query("appid") appid: String): Call<WeatherData>
}