package com.blablacar.technicaltest.feature.trip.search

import androidx.databinding.ObservableBoolean
import io.reactivex.Observable

/**
 * Interface expose to [SearchTripFragment] to limit the interaction with the activity view model.
 * Couldn't find more suitable name.
 */
interface SearchTripViewModel {
    val stateObservable: Observable<SearchTripState>
    val isLoading: ObservableBoolean

    fun searchTrip(departure: String, arrival: String)
}