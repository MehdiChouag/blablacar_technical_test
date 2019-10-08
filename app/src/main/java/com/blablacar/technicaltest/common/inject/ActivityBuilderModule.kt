package com.blablacar.technicaltest.common.inject

import com.blablacar.technicaltest.common.inject.scope.ActivityScope
import com.blablacar.technicaltest.feature.trip.TripActivity
import com.blablacar.technicaltest.feature.trip.common.inject.TripActivityModule
import com.blablacar.technicaltest.feature.trip.common.inject.TripFragmentBuilderModule
import dagger.Module
import dagger.android.ContributesAndroidInjector


/**
 * Provide dagger dependencies to Android dependencies.
 */
@Module
abstract class ActivityBuilderModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [TripActivityModule::class, TripFragmentBuilderModule::class])
    abstract fun bindTripActivity(): TripActivity
}