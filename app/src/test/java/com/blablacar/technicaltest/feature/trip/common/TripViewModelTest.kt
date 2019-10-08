package com.blablacar.technicaltest.feature.trip.common

import com.blablacar.technicaltest.R
import com.blablacar.technicaltest.RxTrampolineRule
import com.blablacar.technicaltest.feature.trip.common.model.Trip
import com.blablacar.technicaltest.feature.trip.common.repository.TripRepository
import com.blablacar.technicaltest.feature.trip.search.ErrorSearchTripState
import com.blablacar.technicaltest.feature.trip.search.SuccessSearchTripState
import io.reactivex.Single
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnit
import retrofit2.HttpException
import retrofit2.Response

class TripViewModelTest {

    @Rule
    @JvmField
    val testSchedulerRule = RxTrampolineRule()

    @Rule
    @JvmField
    val rule = MockitoJUnit.rule()!!

    @Mock
    lateinit var tripRepository: TripRepository

    private val trips: List<Trip> = emptyList()

    private lateinit var tripViewModel: TripViewModel

    @Before
    fun setup() {
        tripViewModel = TripViewModel(tripRepository)
    }

    @Test
    fun `start search with empty cities`() {
        val testObserver = tripViewModel.stateObservable.test()

        tripViewModel.searchTrip("", "")

        testObserver
            .assertValue { it is ErrorSearchTripState }
            .assertNotComplete()
    }

    @Test
    fun `search success`() {
        val testObserver = tripViewModel.stateObservable.test()
        `when`(tripRepository.searchTrip(anyString(), anyString())).thenReturn(Single.just(trips))

        tripViewModel.searchTrip("Lyon", "Rennes")

        testObserver
            .assertValue { it is SuccessSearchTripState }
            .assertNotComplete()

        assertEquals(trips, tripViewModel.tripList)
    }

    @Test
    fun `search failed with 401`() {
        val testObserver = tripViewModel.stateObservable.test()
        val response = createResponse(401)
        `when`(tripRepository.searchTrip(anyString(), anyString())).thenReturn(
            Single.error(
                HttpException(response)
            )
        )

        tripViewModel.searchTrip("Lyon", "Rennes")

        testObserver
            .assertValue {
                it is ErrorSearchTripState && it.errorMessage == R.string.error_not_authenticated
            }
            .assertNotComplete()

        assert(tripViewModel.tripList.isEmpty())
    }

    @Test
    fun `search failed with 404`() {
        val testObserver = tripViewModel.stateObservable.test()
        val response = createResponse(404)
        `when`(tripRepository.searchTrip(anyString(), anyString())).thenReturn(
            Single.error(
                HttpException(response)
            )
        )

        tripViewModel.searchTrip("Lyon", "Rennes")

        testObserver
            .assertValue {
                it is ErrorSearchTripState && it.errorMessage == R.string.error_no_trip_found
            }
            .assertNotComplete()

        assert(tripViewModel.tripList.isEmpty())
    }

    @Test
    fun `search failed unknown error`() {
        val testObserver = tripViewModel.stateObservable.test()
        `when`(tripRepository.searchTrip(anyString(), anyString())).thenReturn(
            Single.error(Exception())
        )

        tripViewModel.searchTrip("Lyon", "Rennes")

        testObserver
            .assertValue {
                it is ErrorSearchTripState && it.errorMessage == R.string.error_generic
            }
            .assertNotComplete()

        assert(tripViewModel.tripList.isEmpty())
    }

    private fun createResponse(code: Int): Response<String> {
        val responseBody = "".toResponseBody("application/json".toMediaType())
        return Response.error(code, responseBody)
    }
}