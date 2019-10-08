package com.blablacar.technicaltest.feature.trip.common.repository

import com.blablacar.technicaltest.feature.trip.common.datasource.TripDataSource
import com.blablacar.technicaltest.feature.trip.common.model.Trip
import io.reactivex.Single

/**
 * This repository will retrieve trips from it's data source.
 */
class TripRepository(private val tripDataSource: TripDataSource) {

    fun searchTrip(departure: String, arrival: String): Single<List<Trip>> {
        return tripDataSource.searchTrip(departure, arrival)
    }
}