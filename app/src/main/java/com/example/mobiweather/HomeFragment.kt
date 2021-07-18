package com.example.mobiweather

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobiweather.adapters.CityDataAdapter
import com.example.mobiweather.adapters.CityDataAdapter.OnRowClickListener
import com.example.mobiweather.models.CityData
import com.example.mobiweather.models.WeatherData
import com.example.mobiweather.viewmodels.WeatherInfoViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.GoogleMap.OnMapClickListener
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.hellohasan.weatherappmvvm.features.weather_info_show.model.WeatherInfoShowModel
import com.hellohasan.weatherappmvvm.features.weather_info_show.model.WeatherInfoShowModelImpl
import java.util.*
import kotlin.collections.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment(), OnMapReadyCallback {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var mMap: GoogleMap
    lateinit var floatBtn : FloatingActionButton
    private lateinit var mRecyclerView : RecyclerView
    private var latLong = LatLng(20.9320, 77.7523)
    private lateinit var model: WeatherInfoShowModel
    private lateinit var viewModel: WeatherInfoViewModel
    private lateinit var cityData : ArrayList<CityData>
    private lateinit var wData : WeatherData
    private lateinit var adapter : CityDataAdapter
    lateinit var navController : NavController

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
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    @SuppressLint("WrongConstant")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        floatBtn =view.findViewById(R.id.fab_add)
        mRecyclerView = view.findViewById(R.id.recycler_view)

        navController = Navigation.findNavController(view)
        // initialize model. (I know we should not initialize model in View. But for simplicity...)
        model = WeatherInfoShowModelImpl(requireContext())
        // initialize ViewModel
        viewModel = ViewModelProviders.of(this).get(WeatherInfoViewModel::class.java)
        setLiveDataListeners()
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        bookMarkCity()
        floatBtn.setOnClickListener(View.OnClickListener {
            val latval = latLong.latitude
            val longval = latLong.longitude
            viewModel.getWeatherInfo(latval, longval, model)
        })

        mRecyclerView.layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)
        cityData = ArrayList<CityData>()
        //creating our adapter
        adapter = CityDataAdapter(cityData)
        //now adding the adapter to recyclerview
        mRecyclerView.adapter = adapter
        adapter.setOnRowClickListener(object : OnRowClickListener {
            override fun onRowClick(position: Int) {
                val directions = HomeFragmentDirections.actionHomeFragmentToCityFragment(wData)
                navController.navigate(directions)
            }
        })
    }

    private fun bookMarkCity() {
       // TODO("Not yet implemented")
        val latval = latLong.latitude
        val longval = latLong.longitude
        viewModel.getWeatherInfo(latval, longval, model)
    }

    private fun setLiveDataListeners() {
        viewModel.weatherInfoLiveData.observe(requireActivity(), Observer { weatherData ->
            wData = weatherData
            setWeatherInfo(weatherData)
        })
    }

    private fun setWeatherInfo(weatherData: WeatherData) {
        cityData.add(CityData(weatherData.name))
        adapter.notifyDataSetChanged()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        //16.699131, 74.229195

        // Add a marker in Sydney and move the camera


        mMap.addMarker(MarkerOptions().position(latLong).title("Marker in Kolhapur"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLong))
        //UiSettings.setZoomControlsEnabled(true)
        mMap.uiSettings.isZoomControlsEnabled=true
        // mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 18F), 5000, null);

        // Setting a click event handler for the map
        mMap.setOnMapClickListener(OnMapClickListener { latLng -> // Creating a marker
            val markerOptions = MarkerOptions()

            latLong = latLng
            // Setting the position for the marker
            markerOptions.position(latLng)

            // Setting the title for the marker.
            // This will be displayed on taping the marker
            markerOptions.title(latLng.latitude.toString() + " : " + latLng.longitude)

            // Clears the previously touched position
            mMap.clear()

            // Animating to the touched position
            mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng))

            // Placing a marker on the touched position
            mMap.addMarker(markerOptions)
        })
    }

}