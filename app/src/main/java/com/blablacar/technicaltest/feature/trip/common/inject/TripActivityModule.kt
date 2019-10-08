package com.blablacar.technicaltest.feature.trip.common.inject

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.blablacar.technicaltest.common.inject.scope.ActivityScope
import com.blablacar.technicaltest.feature.trip.common.TripPageRouter
import com.blablacar.technicaltest.feature.trip.TripActivity
import com.blablacar.technicaltest.feature.trip.common.TripViewModel
import com.blablacar.technicaltest.feature.trip.common.datasource.TripDataSource
import com.blablacar.technicaltest.feature.trip.common.datasource.TripRemoteDataSource
import com.blablacar.technicaltest.feature.trip.common.datasource.TripService
import com.blablacar.technicaltest.feature.trip.common.repository.TripRepository
import com.blablacar.technicaltest.common.viewmodel.key.ViewModelKey
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import retrofit2.Retrofit

/**
 * Module providing dependencies needed by [TripActivity]
 */
@Module
object TripActivityModule {

    @Provides
    @JvmStatic
    fun provideTripService(retrofit: Retrofit): TripService {
        return retrofit.create(TripService::class.java)
    }

    @Provides
    @JvmStatic
    fun provideTripDataSource(tripService: TripService): TripDataSource {
        return TripRemoteDataSource(tripService)
    }

    @Provides
    @JvmStatic
    fun provideTripRepository(tripDataSource: TripDataSource): TripRepository {
        return TripRepository(tripDataSource)
    }

    @Provides
    @JvmStatic
    @ActivityScope
    fun provideTripPageRouter(tripActivity: TripActivity): TripPageRouter {
        return TripPageRouter(tripActivity.supportFragmentManager)
    }

    @Provides
    @JvmStatic
    @IntoMap
    @ViewModelKey(TripViewModel::class)
    fun provideTripViewModelFactory(tripRepository: TripRepository): ViewModel {
        return TripViewModel(tripRepository)
    }

    @Provides
    @JvmStatic
    @ActivityScope
    fun provideTripViewModel(
        tripActivity: TripActivity,
        viewModelFactory: ViewModelProvider.Factory
    ): TripViewModel {
        return ViewModelProvider(tripActivity, viewModelFactory).get(TripViewModel::class.java)
    }
}