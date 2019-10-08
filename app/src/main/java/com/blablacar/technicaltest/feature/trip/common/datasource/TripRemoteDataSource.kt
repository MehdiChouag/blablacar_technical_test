package com.blablacar.technicaltest.feature.trip.common.datasource

import com.blablacar.technicaltest.feature.trip.common.model.Trip
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

/**
 * Data source fetching trips from network.
 */
class TripRemoteDataSource(private val tripService: TripService) : TripDataSource {

    override fun searchTrip(departure: String, destination: String): Single<List<Trip>> {
        return tripService.searchTrip(departure, destination).subscribeOn(Schedulers.io())
            .map { it.trips }
    }
}