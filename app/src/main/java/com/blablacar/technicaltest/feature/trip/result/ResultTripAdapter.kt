package com.blablacar.technicaltest.feature.trip.result

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.blablacar.technicaltest.R
import com.blablacar.technicaltest.databinding.ItemTripBinding
import com.blablacar.technicaltest.feature.trip.common.model.Trip

class ResultTripAdapter(private val trips: List<Trip>) : RecyclerView.Adapter<ResultViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemTripBinding>(
            layoutInflater,
            R.layout.item_trip,
            parent,
            false
        )
        return ResultViewHolder(binding)
    }

    override fun getItemCount(): Int = trips.size

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        val item = trips[position]
        holder.itemTripBinding.setUsername(item.user.name)
        holder.itemTripBinding.pictureUrl = item.user.pictureUrl
        holder.itemTripBinding.setTrip("${item.departure.city} → ${item.arrival.city}")
        holder.itemTripBinding.setPrice(item.price.price)
    }
}