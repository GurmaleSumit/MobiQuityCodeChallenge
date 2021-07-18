package com.example.mobiweather

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.mobiweather.models.WeatherData
import com.example.mobiweather.utils.kelvinToCelsius
import com.example.mobiweather.utils.unixTimestampToDateTimeString


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CityFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CityFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var weatherData : WeatherData
    private val args : CityFragmentArgs by navArgs()
    private lateinit var tvTemperature : TextView
    private lateinit var tvCityCountry : TextView
    private lateinit var tvDateTime : TextView
    private lateinit var ivWeatherCondition : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        // This callback will only be called when MyFragment is at least Started.
        // This callback will only be called when MyFragment is at least Started.
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {
                    // Handle the back button event
                    Log.d("ass","Dsds");
                    findNavController().navigate(R.id.action_cityFragment2_to_homeFragment22)
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(callback)

        return inflater.inflate(R.layout.fragment_city, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        weatherData = args.weather
        view.apply {
            tvTemperature = findViewById(R.id.tv_temperature)
            tvCityCountry = findViewById(R.id.tv_city_country)
            tvDateTime = findViewById(R.id.tv_date_time)
            ivWeatherCondition = findViewById(R.id.iv_weather_condition)

            //"http://openweathermap.org/img/w/${data.weather[0].icon}.png",
        }

        setWeatherInfo(weatherData)
    }

    private fun setWeatherInfo(weatherData: WeatherData) {
       /* output_group.visibility = View.VISIBLE
        tv_error_message.visibility = View.GONE

        tv_date_time?.text = weatherData.dateTime
        tv_temperature?.text = weatherData.temperature
        tv_city_country?.text = weatherData.cityAndCountry
        Glide.with(this).load(weatherData.weatherConditionIconUrl).into(iv_weather_condition)
        tv_weather_condition?.text = weatherData.weatherConditionIconDescription

        tv_humidity_value?.text = weatherData.humidity
        tv_pressure_value?.text = weatherData.pressure
        tv_visibility_value?.text = weatherData.visibility

        tv_sunrise_time?.text = weatherData.sunrise
        tv_sunset_time?.text = weatherData.sunset*/
        var weatherConditionIconUrl = "http://openweathermap.org/img/w/${weatherData.weather[0].icon}.png"
       // tvTemperature.setText(weatherData.main.temp.kelvinToCelsius().toString())
      //  tvCityCountry.setText(weatherData.name)
       // tvCityCountry.setText(weatherData.dateTime.unixTimestampToDateTimeString())

        tvTemperature?.text = weatherData.main.temp.kelvinToCelsius().toString()
        tvCityCountry.text = weatherData.name
        tvDateTime.text = weatherData.dateTime.unixTimestampToDateTimeString()
        Glide.with(this).load(weatherConditionIconUrl).into(ivWeatherCondition)
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CityFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CityFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}