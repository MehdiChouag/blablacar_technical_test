package com.blablacar.technicaltest.feature.trip

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.blablacar.technicaltest.R
import com.blablacar.technicaltest.feature.trip.common.TripPageRouter
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.HasDispatchingSupportFragmentInjector
import javax.inject.Inject

/**
 * Activity holding both [SearchTripFragment] & [ResultTripFragment]
 */
class TripActivity : AppCompatActivity(), HasAndroidInjector,
    HasDispatchingSupportFragmentInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>
    @Inject
    lateinit var dispatchFragmentInjector: DispatchingAndroidInjector<Fragment>
    @Inject
    lateinit var tripPageRouter: TripPageRouter

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            tripPageRouter.displaySearchTripPage()
        }
    }

    override fun androidInjector(): AndroidInjector<Any> {
        return dispatchingAndroidInjector
    }

    override fun supportFragmentInjector(): DispatchingAndroidInjector<Fragment> {
        return dispatchFragmentInjector
    }
}
