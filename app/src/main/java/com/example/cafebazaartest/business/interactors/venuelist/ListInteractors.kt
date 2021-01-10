package com.example.cafebazaartest.business.interactors.venuelist

class ListInteractors (
    val cleanCache: CleanCache,
    val getVenueListFromCache: GetVenueListFromCache,
    val getVenueListFromNetwork: GetVenueListFromNetwork,
    val addVenueListToCache: AddVenueListToCache


)