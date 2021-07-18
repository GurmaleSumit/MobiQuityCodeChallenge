package com.example.mobiweather.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mobiweather.R
import com.example.mobiweather.models.CityData
import java.util.*

class CityDataAdapter (val userList: ArrayList<CityData>) :
    RecyclerView.Adapter<CityDataAdapter.ViewHolder>() {

    private lateinit var mListener : OnRowClickListener

    public interface OnRowClickListener {
        fun onRowClick(position: Int);
    }

    fun setOnRowClickListener (listener : OnRowClickListener){
       mListener = listener
    }

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityDataAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return ViewHolder(v,mListener)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: CityDataAdapter.ViewHolder, position: Int) {
        holder.bindItems(userList[position])
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return userList.size
    }

    //the class is hodling the list view
   inner class ViewHolder(itemView: View, listener : OnRowClickListener) : RecyclerView.ViewHolder(itemView) {


        init {
             itemView.setOnClickListener {
                 listener.onRowClick(adapterPosition)
             }
        }

        fun bindItems(cityData: CityData) {
          /*  val textViewName = itemView.findViewById(R.id.passcode_name) as TextView
            val textViewAddress  = itemView.findViewById(R.id.passcode_type) as TextView
            var passcodeTimeDays = itemView.findViewById(R.id.passcode_time_days) as TextView
            textViewName.text = user.passcodeName
            textViewAddress.text = user.passcodeType
            //passcodeTimeDays.text = user.dayList.toString()
            passcodeTimeDays.text = user.startTime+" - "+user.endTime*/
            val  cityName = itemView.findViewById(R.id.cityName) as TextView
            cityName.text = cityData.cityName
        }

    }
}