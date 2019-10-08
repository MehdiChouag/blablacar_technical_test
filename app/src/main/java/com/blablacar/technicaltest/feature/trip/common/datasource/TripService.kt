package com.blablacar.technicaltest.feature.trip.common.datasource

import com.blablacar.technicaltest.feature.trip.common.model.SearchTrip
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Retrofit service defining the end point to fetch trips.
 */
interface TripService {
    @GET("trips?_format=json&locale=fr_FR&cur=EUR")
    fun searchTrip(@Query("fn") departure: String, @Query("tn") destination: String): Single<SearchTrip>
}