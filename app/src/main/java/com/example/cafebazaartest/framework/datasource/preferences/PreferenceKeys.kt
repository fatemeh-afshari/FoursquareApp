package com.example.cafebazaartest.framework.datasource.preferences

import com.example.cafebazaartest.framework.datasource.preferences.PreferenceKeys.Companion.VENUE_PREFERENCES

class PreferenceKeys {
    companion object{

        // Shared Preference Files:
        const val VENUE_PREFERENCES: String = "com.example.cafebazaartest"

        // Shared Preference Keys
        val CURRENT_LATITUDE: String = "${VENUE_PREFERENCES}.CURRENT_LATITUDE"
        val CURRENT_LONGITUDE: String = "${VENUE_PREFERENCES}.CURRENT_LONGITUDE"
        val LOCAL_DATABASE_COUNT: String = "${VENUE_PREFERENCES}.LOCAL_DATABASE_COUNT"

    }
}