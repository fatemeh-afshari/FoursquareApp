package com.example.cafebazaartest.di

import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProvider
import com.example.cafebazaartest.business.interactors.venuedetail.DetailInteractors
import com.example.cafebazaartest.business.interactors.venuelist.ListInteractors
import com.example.cafebazaartest.framework.presentation.common.ViewModelFactory
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@FlowPreview
@Module
object ViewModelModule {

    @Singleton
    @JvmStatic
    @Provides
    fun provideViewModelFactory(
        listInteractors: ListInteractors,
        detailInteractors: DetailInteractors

    ): ViewModelProvider.Factory {
        return ViewModelFactory(
            listInteractors = listInteractors,
            detailInteractors = detailInteractors
        )
    }

}
