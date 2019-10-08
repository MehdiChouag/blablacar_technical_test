package com.blablacar.technicaltest.feature.trip.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.blablacar.technicaltest.R
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_result_trip.*
import javax.inject.Inject

/**
 * Fragment displaying the list of trips.
 */
class ResultTripFragment : Fragment() {

    @Inject lateinit var adapter: ResultTripAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_result_trip, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        recycler.adapter = adapter
    }

    companion object {
        val TAG = ResultTripFragment::class.simpleName

        @JvmStatic
        fun newInstance() = ResultTripFragment()
    }
}