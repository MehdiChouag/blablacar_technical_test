package com.blablacar.technicaltest.feature.trip.search

import androidx.annotation.StringRes

/**
 * Defined the different state on with [SearchTripFragment] will have to react.
 */
sealed class SearchTripState
object SuccessSearchTripState : SearchTripState()
data class ErrorSearchTripState(@StringRes val errorMessage: Int) : SearchTripState()
