package com.example.cafebazaartest.framework.presentation.common

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cafebazaartest.business.interactors.venuedetail.DetailInteractors
import com.example.cafebazaartest.business.interactors.venuelist.ListInteractors
import com.example.cafebazaartest.framework.presentation.detial.VenueDetailViewModel
import com.example.cafebazaartest.framework.presentation.list.VenueListViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
class ViewModelFactory
constructor(
    private val listInteractors: ListInteractors,
    private val detailInteractors: DetailInteractors
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when(modelClass){

            VenueListViewModel::class.java -> {
                VenueListViewModel(
                    interactors = listInteractors
                ) as T
            }
            VenueDetailViewModel::class.java -> {
                VenueDetailViewModel(
                    interactors = detailInteractors
                ) as T
            }

            else -> {
                throw IllegalArgumentException("unknown model class $modelClass")
            }
        }
    }
}