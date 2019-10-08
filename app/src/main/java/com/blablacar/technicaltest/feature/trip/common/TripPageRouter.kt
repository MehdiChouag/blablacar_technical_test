package com.blablacar.technicaltest.feature.trip.common

import androidx.fragment.app.FragmentManager
import com.blablacar.technicaltest.R
import com.blablacar.technicaltest.feature.trip.result.ResultTripFragment
import com.blablacar.technicaltest.feature.trip.search.SearchTripFragment

class TripPageRouter(private val fragmentManager: FragmentManager) {

    fun displaySearchTripPage() {
        val fragment = fragmentManager.findFragmentByTag(SearchTripFragment.TAG)
            ?: SearchTripFragment.newInstance()
        fragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment, SearchTripFragment.TAG)
            .commit()
    }

    fun displayResultTripPage() {
        val fragment = fragmentManager.findFragmentByTag(ResultTripFragment.TAG)
            ?: ResultTripFragment.newInstance()
        fragmentManager.beginTransaction()
            .setCustomAnimations(
                android.R.anim.slide_in_left,
                android.R.anim.slide_out_right,
                android.R.anim.slide_in_left,
                android.R.anim.slide_out_right
            )
            .add(R.id.fragment_container, fragment, ResultTripFragment.TAG)
            .addToBackStack(ResultTripFragment.TAG)
            .commit()
    }
}