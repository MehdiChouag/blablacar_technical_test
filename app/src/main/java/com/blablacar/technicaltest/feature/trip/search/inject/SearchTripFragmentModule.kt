package com.blablacar.technicaltest.feature.trip.search.inject

import com.blablacar.technicaltest.feature.trip.common.TripViewModel
import com.blablacar.technicaltest.feature.trip.search.SearchTripViewModel
import dagger.Binds
import dagger.Module

/**
 * Provide [SearchTripViewModel] in order to stick to SOLID principles.
 */
@Module
abstract class SearchTripFragmentModule {
    @Binds
    abstract fun provideSearchTripViewModel(tripViewModel: TripViewModel): SearchTripViewModel
}