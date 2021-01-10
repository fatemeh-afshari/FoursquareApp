package com.example.cafebazaartest.framework.presentation.common

import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Inject
import androidx.fragment.app.FragmentFactory
import com.example.cafebazaartest.framework.presentation.detial.DetailFragment
import com.example.cafebazaartest.framework.presentation.list.ListFragment
import com.example.cafebazaartest.util.SharedPreferencesHelper

@ExperimentalCoroutinesApi
@FlowPreview
class VenueFragmentFactory
@Inject
constructor(
    private val viewModelFactory: ViewModelProvider.Factory,
private val pref : SharedPreferencesHelper
): FragmentFactory(){

    override fun instantiate(classLoader: ClassLoader, className: String) =

        when(className){

            ListFragment::class.java.name -> {
                val fragment = ListFragment(viewModelFactory,pref)
                fragment
            }
            DetailFragment::class.java.name -> {
                val fragment = DetailFragment(viewModelFactory)
                fragment
            }

            else -> {
                super.instantiate(classLoader, className)
            }
        }
}