package com.blablacar.technicaltest.feature.trip.result.inject

import com.blablacar.technicaltest.feature.trip.common.TripViewModel
import com.blablacar.technicaltest.feature.trip.result.ResultTripAdapter
import com.blablacar.technicaltest.common.inject.scope.FragmentScope
import dagger.Module
import dagger.Provides

@Module
object ResultTripFragmentModule {

    @Provides
    @JvmStatic
    @FragmentScope
    fun provideResultTripAdapter(tripViewModel: TripViewModel): ResultTripAdapter {
        return ResultTripAdapter(tripViewModel.tripList)
    }

}