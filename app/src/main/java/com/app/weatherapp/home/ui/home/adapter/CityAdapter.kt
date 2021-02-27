package com.app.weatherapp.home.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.weatherapp.databinding.ItemCityBinding
import com.app.weatherapp.responseobject.City

class CityAdapter(private val cityListener: CityListener) :
    ListAdapter<City, CityAdapter.ViewHolder>(
        DiffCallBack
    ) {


    class ViewHolder(private val binding: ItemCityBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            cityListener: CityListener,
            city: City
        ) {


            binding.city = city
            binding.executePendingBindings()

            itemView.setOnClickListener {
                cityListener.onCityClick(city)
            }

        }
    }

    companion object DiffCallBack :
        DiffUtil.ItemCallback<City>() {

        override fun areItemsTheSame(oldItem: City, newItem: City): Boolean {
            return oldItem.geonameid == newItem.geonameid
        }

        override fun areContentsTheSame(oldItem: City, newItem: City): Boolean {
            return oldItem == newItem
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemCityBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val volunteer = getItem(position)

        holder.itemView.setOnClickListener {
            cityListener.onCityClick(volunteer)
        }
        holder.bind(cityListener, volunteer)

    }

    interface CityListener {

        fun onCityClick(city: City)

    }
}