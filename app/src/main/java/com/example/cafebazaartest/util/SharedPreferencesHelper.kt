package com.example.cafebazaartest.util

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.core.content.edit
import com.google.android.gms.maps.model.LatLng

class SharedPreferencesHelper {

    companion object {

        // Shared Preference Files:
        const val VENUE_PREFERENCES: String = "com.example.cafebazaartest"

        // Shared Preference Keys
        val CURRENT_LATITUDE: String = "${VENUE_PREFERENCES}.CURRENT_LATITUDE"
        val CURRENT_LONGITUDE: String = "${VENUE_PREFERENCES}.CURRENT_LONGITUDE"
        val LOCAL_DATABASE_COUNT: String = "${VENUE_PREFERENCES}.LOCAL_DATABASE_COUNT"
        private var prefs: SharedPreferences? = null

        @Volatile private var instance: SharedPreferencesHelper? = null
        private val LOCK = Any()

        operator fun invoke(context: Context): SharedPreferencesHelper = instance ?: synchronized(LOCK) {
            instance ?: buildHelper(context).also {
                instance = it
            }
        }

        private fun buildHelper(context: Context) : SharedPreferencesHelper {
            prefs = PreferenceManager.getDefaultSharedPreferences(context)
            return SharedPreferencesHelper()
        }
    }


    fun saveCurrentLocation(currentLocation: LatLng) {
        prefs?.edit(commit = true) {putString(CURRENT_LATITUDE, "${currentLocation.latitude}")}
        prefs?.edit(commit = true) {putString(CURRENT_LONGITUDE, "${currentLocation.longitude}")}
    }
    fun getCurrentLocation(): LatLng {
        return LatLng(prefs?.getString(CURRENT_LATITUDE, "0.0")!!.toDouble() ,prefs?.getString(CURRENT_LONGITUDE, "0.0")!!.toDouble())

    }
    fun saveLocalDatabaseCount(count:Int) {
        prefs?.edit(commit = true) {putInt(LOCAL_DATABASE_COUNT, count)}

    }
    fun getLocalDatabaseCount(): Int {
        return prefs?.getInt(LOCAL_DATABASE_COUNT, 0)!!
    }

}