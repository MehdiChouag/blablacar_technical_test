package com.blablacar.technicaltest.feature.trip.common

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel
import com.blablacar.technicaltest.R
import com.blablacar.technicaltest.common.viewmodel.state.DataState
import com.blablacar.technicaltest.common.viewmodel.state.State
import com.blablacar.technicaltest.feature.trip.common.model.Trip
import com.blablacar.technicaltest.feature.trip.common.repository.TripRepository
import com.blablacar.technicaltest.feature.trip.search.ErrorSearchTripState
import com.blablacar.technicaltest.feature.trip.search.SearchTripState
import com.blablacar.technicaltest.feature.trip.search.SearchTripViewModel
import com.blablacar.technicaltest.feature.trip.search.SuccessSearchTripState
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.subjects.PublishSubject
import retrofit2.HttpException

class TripViewModel(private val tripRepository: TripRepository) : ViewModel(), SearchTripViewModel {

    private val stateSubject: PublishSubject<SearchTripState> = PublishSubject.create()
    private val compositeDisposable = CompositeDisposable()

    override val stateObservable: Observable<SearchTripState>
        get() = stateSubject.hide()

    override val isLoading: ObservableBoolean = ObservableBoolean(false)

    var tripList: List<Trip> = emptyList()

    override fun searchTrip(departure: String, arrival: String) {
        if (departure.isEmpty() || arrival.isEmpty()) {
            stateSubject.onNext(ErrorSearchTripState(R.string.error_city_input))
        } else {
            val disposable = tripRepository.searchTrip(departure, arrival)
                .toObservable()
                .map(State.mapToSuccess())
                .onErrorReturn(State.mapToError())
                .startWith(State.loading())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumeTrip())
            compositeDisposable.add(disposable)
        }
    }

    private fun consumeTrip() = Consumer<State<List<Trip>>> { state ->
        when (state.state) {
            DataState.LOADING -> {
                isLoading.set(true)
            }
            DataState.SUCCESS -> {
                isLoading.set(false)
                tripList = state.data!!
                stateSubject.onNext(SuccessSearchTripState)
            }
            DataState.ERROR -> {
                isLoading.set(false)
                handleError(state.error!!)
            }
        }
    }

    private fun handleError(exception: Exception) {
        var errorStringId = R.string.error_generic
        if (exception is HttpException) {
            when (exception.code()) {
                404 -> errorStringId = R.string.error_no_trip_found
                401 -> errorStringId = R.string.error_not_authenticated
            }
        }
        stateSubject.onNext(
            ErrorSearchTripState(
                errorStringId
            )
        )
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}