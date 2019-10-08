package com.blablacar.technicaltest.feature.trip.common.datasource

import com.blablacar.technicaltest.feature.trip.common.model.Trip
import io.reactivex.Single

/**
 * Interface used to abstract the data source (Network, Cache or DB)
 */
interface TripDataSource {
    fun searchTrip(departure: String, destination: String): Single<List<Trip>>
}