package com.example.cafebazaartest.framework.presentation.list

import android.location.Location
import android.location.LocationListener
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cafebazaartest.R
import com.example.cafebazaartest.business.domain.model.Venue
import com.example.cafebazaartest.business.domain.state.StateMessageCallback
import com.example.cafebazaartest.framework.presentation.common.BaseFragment
import com.example.cafebazaartest.util.LIMIT
import com.example.cafebazaartest.util.QUERY
import com.example.cafebazaartest.util.SharedPreferencesHelper

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Inject

@FlowPreview
@ExperimentalCoroutinesApi
class ListFragment
constructor(
    private val viewModelFactory: ViewModelProvider.Factory,
    private val pref: SharedPreferencesHelper
) : BaseFragment(R.layout.fragment_list), LocationListener {
    private lateinit var mMap: GoogleMap
    private var currentLocation: LatLng = LatLng(-34.0, 151.0)
    private val callback = OnMapReadyCallback { googleMap ->
        mMap = googleMap
    }
    val viewModel: VenueListViewModel by viewModels {
        viewModelFactory
    }


    private var listAdapter: ListAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listAdapter = ListAdapter(ArrayList<Venue>())
        viewModel.setupChannel()
    }

    @InternalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
        setUpRecyclerView()
        subscribeObservers()
        listAdapter?.clear()
        viewModel.getPermission(activity!!)
    }

    override fun inject() {
        getAppComponent().inject(this)
    }


    private fun setUpRecyclerView() {
        recycler_view.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false);
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val lastPosition = layoutManager.findLastVisibleItemPosition()
                    if (lastPosition == listAdapter?.itemCount?.minus(1)) {
                        if (listAdapter!!.itemCount <= 45) {
                            viewModel.retriveVenues(
                                currentLocation.latitude, currentLocation.longitude, LIMIT, QUERY,
                                listAdapter?.itemCount!!
                            )
                        }
                    }
                }
            })
            adapter = listAdapter
        }
    }

    private fun subscribeObservers() {
        viewModel.isPermissionsGranted.observe(
            viewLifecycleOwner,
            Observer { ispPermissionGranted ->
                if (ispPermissionGranted) {
                    viewModel.getCurrentLocation(context, viewLifecycleOwner)
                }
            })
        viewModel.currentLocation.observe(viewLifecycleOwner, Observer { location ->
            if (viewModel.getDistance(pref.getCurrentLocation(), location) > 100 ||pref.getLocalDatabaseCount()==0) {

                currentLocation = location
                viewModel.retriveVenues(
                    currentLocation.latitude,
                    currentLocation.longitude,
                    LIMIT,
                    QUERY,
                    0
                )
            }else{
               viewModel.retriveVenuesFromCache()
            }
            pref.saveCurrentLocation(location)
        })
        viewModel.viewState.observe(viewLifecycleOwner, Observer { viewState ->

            if (viewState != null) {
                viewState.venues?.let { venues ->
                    pref.saveLocalDatabaseCount(pref.getLocalDatabaseCount() + LIMIT)
                    listAdapter?.updateList(venues)
                    listAdapter?.notifyDataSetChanged()
                    mMap.animateCamera(
                        CameraUpdateFactory.newLatLngZoom(
                            currentLocation,
                            12.0f - (listAdapter!!.itemCount / 10)
                        )
                    )
                    venues.forEach { venue ->
                        mMap.addMarker(
                            MarkerOptions().position(
                                LatLng(
                                    venue.location.lat,
                                    venue.location.lng
                                )
                            )
                        )
                    }
                }
            }
        })

        viewModel.shouldDisplayProgressBar.observe(viewLifecycleOwner, Observer {
            uiController.displayProgressBar(it)
        })

        viewModel.stateMessage.observe(viewLifecycleOwner, Observer { stateMessage ->

            stateMessage?.response?.let { response ->

                uiController.onResponseReceived(
                    response = stateMessage.response,
                    stateMessageCallback = object : StateMessageCallback {
                        override fun removeMessageFromStack() {
                            viewModel.clearStateMessage()
                        }
                    }
                )

            }

        })

    }

    override fun onLocationChanged(p0: Location) {
        TODO("Not yet implemented")
    }
}