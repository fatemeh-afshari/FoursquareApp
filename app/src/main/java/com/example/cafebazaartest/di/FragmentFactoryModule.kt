package com.example.cafebazaartest.di

import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.ViewModelProvider
import com.example.cafebazaartest.framework.presentation.common.VenueFragmentFactory
import com.example.cafebazaartest.util.SharedPreferencesHelper
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Singleton

@FlowPreview
@ExperimentalCoroutinesApi
@Module
object FragmentFactoryModule {

    @JvmStatic
    @Singleton
    @Provides
    fun provideListFragmentFactory(
        viewModelFactory: ViewModelProvider.Factory,
         pref : SharedPreferencesHelper
    ): FragmentFactory {
        return VenueFragmentFactory(
            viewModelFactory,
            pref
        )
    }
}