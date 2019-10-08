package com.blablacar.technicaltest.feature.trip.common.inject

import com.blablacar.technicaltest.common.inject.scope.FragmentScope
import com.blablacar.technicaltest.feature.trip.result.ResultTripFragment
import com.blablacar.technicaltest.feature.trip.result.inject.ResultTripFragmentModule
import com.blablacar.technicaltest.feature.trip.search.SearchTripFragment
import com.blablacar.technicaltest.feature.trip.search.inject.SearchTripFragmentModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Provide dependencies to the fragments.
 */
@Module
abstract class TripFragmentBuilderModule {

    @FragmentScope
    @ContributesAndroidInjector(modules = [SearchTripFragmentModule::class])
    abstract fun bindSearchTripFragment(): SearchTripFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [ResultTripFragmentModule::class])
    abstract fun bindResultTripFragment(): ResultTripFragment
}