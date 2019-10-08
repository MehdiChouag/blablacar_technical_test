package com.blablacar.technicaltest.feature.trip.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.blablacar.technicaltest.R
import com.blablacar.technicaltest.common.ui.hideKeyboard
import com.blablacar.technicaltest.databinding.FragmentSearchTripBinding
import com.blablacar.technicaltest.feature.trip.common.TripPageRouter
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.AndroidSupportInjection
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_search_trip.*
import javax.inject.Inject


/**
 * Fragment to search a trip by providing a departure & an arrival city.
 */
class SearchTripFragment : Fragment(), View.OnClickListener {

    @Inject
    lateinit var tripViewModel: SearchTripViewModel

    @Inject
    lateinit var tripPageRouter: TripPageRouter

    private var disposable: Disposable? = null

    private val departureCity: String
        get() = departures_city.editText?.text.toString()
    private val arrivalCity: String
        get() = arrival_city.editText?.text.toString()

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentSearchTripBinding>(
            inflater,
            R.layout.fragment_search_trip,
            container,
            false
        )

        disposable = tripViewModel
            .stateObservable
            .subscribe { viewState ->
                when (viewState) {
                    is SuccessSearchTripState -> tripPageRouter.displayResultTripPage()
                    is ErrorSearchTripState -> displayErrorMessage(viewState.errorMessage)
                }
            }

        binding.clickListener = this
        binding.viewModel = tripViewModel

        return binding.root
    }

    private fun displayErrorMessage(@StringRes error: Int) {
        Snackbar.make(view!!, error, Snackbar.LENGTH_SHORT).show()
    }

    private fun hideKeyboardAndClearFocus() {
        arrival_city.editText?.clearFocus()
        departures_city.editText?.clearFocus()
        view?.hideKeyboard()
    }

    override fun onClick(v: View?) {
        hideKeyboardAndClearFocus()
        tripViewModel.searchTrip(departureCity, arrivalCity)
    }

    override fun onDestroyView() {
        disposable?.dispose()
        super.onDestroyView()
    }

    companion object {
        val TAG = SearchTripFragment::class.simpleName

        @JvmStatic
        fun newInstance() = SearchTripFragment()
    }
}
