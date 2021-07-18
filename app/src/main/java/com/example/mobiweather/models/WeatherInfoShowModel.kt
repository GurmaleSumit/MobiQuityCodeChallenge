package com.hellohasan.weatherappmvvm.features.weather_info_show.model

import com.example.mobiweather.listeners.RequestCompleteListener
import com.example.mobiweather.models.WeatherData


interface WeatherInfoShowModel {
    //fun getCityList(callback: RequestCompleteListener<MutableList<City>>)
    fun getWeatherInfo(latitude: Double, longitude: Double, callback: RequestCompleteListener<WeatherData>)
}