package com.example.cafebazaartest.framework.presentation.list

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.emreeran.locationlivedata.LocationLiveData
import com.example.cafebazaartest.business.domain.model.Venue
import com.example.cafebazaartest.business.domain.state.DataState
import com.example.cafebazaartest.business.domain.state.StateEvent
import com.example.cafebazaartest.business.interactors.venuelist.ListInteractors
import com.example.cafebazaartest.framework.presentation.common.BaseViewModel
import com.example.cafebazaartest.framework.presentation.list.state.VenueListStateEvent.GetVenueListEvent
import com.example.cafebazaartest.framework.presentation.list.state.VenueListStateEvent.GetVenueListEventFromCache
import com.example.cafebazaartest.framework.presentation.list.state.VenueListViewState
import com.example.cafebazaartest.util.DEFAULT_LOCATION
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.maps.model.LatLng
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton


@ExperimentalCoroutinesApi
@FlowPreview
@Singleton
class VenueListViewModel
@Inject
constructor(
    private val interactors: ListInteractors
) : BaseViewModel<VenueListViewState>() {

    val currentLocation = MutableLiveData<LatLng>()
    val isPermissionsGranted = MutableLiveData<Boolean>()

    override fun handleNewData(data: VenueListViewState) {
        data.let { viewState ->
            viewState.venues?.let { venueList ->
                setVenueListData(venueList)
            }

        }

    }

    override fun setStateEvent(stateEvent: StateEvent) {

        val job: Flow<DataState<VenueListViewState>?> = when (stateEvent) {

            is GetVenueListEvent -> {
                interactors.getVenueListFromNetwork.getVenuseList(
                    lat = stateEvent.lat,
                    lon = stateEvent.lon,
                    limit = stateEvent.limit,
                    query = stateEvent.query,
                    offset = stateEvent.offset,
                    stateEvent = stateEvent
                )
            }
            is GetVenueListEventFromCache -> {
                interactors.getVenueListFromCache. getVenuseList(
                    stateEvent = stateEvent
                )
            }
            else -> {
                emitInvalidStateEvent(stateEvent)
            }
        }
        launchJob(stateEvent, job)

    }

    override fun initNewViewState(): VenueListViewState {
        return VenueListViewState()
    }

    private fun setVenueListData(venues: ArrayList<Venue>) {
        val update = getCurrentViewStateOrNew()
        update.venues = venues
        setViewState(update)
    }

    fun retriveVenues(lat: Double, lon: Double, limit: Int, query: String, offset: Int) {
        setStateEvent(
            GetVenueListEvent(
                lat = lat,
                lon = lon,
                limit = limit,
                query = query,
                offset = offset
            )
        )
    }
    fun retriveVenuesFromCache() {
        setStateEvent(
            GetVenueListEventFromCache(
            )
        )
    }
//Todo refactor it ...
    fun getCurrentLocation(context: Context?, lifeCycleOwner: LifecycleOwner) {
        val locationLiveData = LocationLiveData.create(
            context!!,
            interval = 500,
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY,
            expirationTime = 10000,
            fastestInterval = 100,
            maxWaitTime = 1000,
            numUpdates = 10,
            smallestDisplacement = 10f,
            onErrorCallback = object : LocationLiveData.OnErrorCallback {
                override fun onLocationSettingsException(e: ApiException) {
                    currentLocation.value = DEFAULT_LOCATION
                }

                override fun onPermissionsMissing() {
                    currentLocation.value = DEFAULT_LOCATION
                }
            }
        )

        locationLiveData.observe(lifeCycleOwner, Observer {   // Where this is a lifecycle owner
            // Do something with location update
            currentLocation.value = LatLng(it.latitude, it.longitude)
        })
    }

    fun checkPermissions(context: Context): Boolean {
        val accessFineLocationPermissionState = ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        val accessCoarseLocationPermissionState = ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        return accessFineLocationPermissionState == PackageManager.PERMISSION_GRANTED && accessCoarseLocationPermissionState == PackageManager.PERMISSION_GRANTED
    }

    fun getPermission(context: Activity) {
        val permissions: ArrayList<String> = ArrayList()
        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION)
        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION)
        Dexter.withActivity(context).withPermissions(permissions)
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    if (checkPermissions(context)) {
                        isPermissionsGranted.value = true
                    } else {
                        currentLocation.value = DEFAULT_LOCATION
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<PermissionRequest>?,
                    token: PermissionToken?
                ) {
                    TODO("Not yet implemented")
                }
            }).check()


    }

    fun getDistance(currentPosition: LatLng, newPosition: LatLng): Int {
        val earthRadius = 3958.75
        val latDiff = Math.toRadians(newPosition.latitude - currentPosition.latitude)
        val lngDiff = Math.toRadians(newPosition.longitude - currentPosition.longitude)
        val a = Math.sin(latDiff / 2) * Math.sin(latDiff / 2) +
                Math.cos(Math.toRadians(currentPosition.latitude)) * Math.cos(
            Math.toRadians(
                newPosition.latitude
            )
        ) *
                Math.sin(lngDiff / 2) * Math.sin(lngDiff / 2)
        val c =
            2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
        val distance = earthRadius * c

        val meterConversion = 1609

        return (distance * meterConversion.toFloat()).toInt()
    }

}