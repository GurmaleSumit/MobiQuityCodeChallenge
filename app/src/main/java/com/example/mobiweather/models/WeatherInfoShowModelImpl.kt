package com.hellohasan.weatherappmvvm.features.weather_info_show.model

import android.content.Context
import com.example.mobiweather.listeners.RequestCompleteListener
import com.example.mobiweather.models.WeatherData
import com.example.mobiweather.network.ApiInterface
import com.example.mobiweather.network.RetrofitClient
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class WeatherInfoShowModelImpl(private val context: Context): WeatherInfoShowModel {

   /* override fun getCityList(callback: RequestCompleteListener<MutableList<City>>) {
        try {
            val stream = context.assets.open("city_list.json")

            val size = stream.available()
            val buffer = ByteArray(size)
            stream.read(buffer)
            stream.close()
            val tContents  = String(buffer)

            val groupListType = object : TypeToken<ArrayList<City>>() {}.type
            val gson = GsonBuilder().create()
            val cityList: MutableList<City> = gson.fromJson(tContents, groupListType)

            callback.onRequestSuccess(cityList) //let presenter know the city list

        } catch (e: IOException) {
            e.printStackTrace()
            callback.onRequestFailed(e.localizedMessage!!) //let presenter know about failure
        }
    }*/

    override fun getWeatherInfo(latitude: Double, longitude: Double, callback: RequestCompleteListener<WeatherData>) {

        val appID = "fae7190d7e6433ec3a45285ffcf55c86"
        val apiInterface: ApiInterface = RetrofitClient.client.create(ApiInterface::class.java)
        val call: Call<WeatherData> = apiInterface.callApiForWeatherInfo(latitude, longitude, appID)

        call.enqueue(object : Callback<WeatherData> {

            // if retrofit network call success, this method will be triggered
            override fun onResponse(call: Call<WeatherData>, response: Response<WeatherData>) {
                if (response.body() != null)
                    callback.onRequestSuccess(response.body()!!) //let presenter know the weather information data
                else
                    callback.onRequestFailed(response.message()) //let presenter know about failure
            }

            // this method will be triggered if network call failed
            override fun onFailure(call: Call<WeatherData>, t: Throwable) {
                callback.onRequestFailed(t.localizedMessage!!) //let presenter know about failure
            }
        })
    }
}