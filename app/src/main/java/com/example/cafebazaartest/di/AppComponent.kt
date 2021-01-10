package com.example.cafebazaartest.di

import com.example.cafebazaartest.business.domain.model.DetailResponse
import com.example.cafebazaartest.framework.presentation.BaseApplication
import com.example.cafebazaartest.framework.presentation.MainActivity
import com.example.cafebazaartest.framework.presentation.detial.DetailFragment
import com.example.cafebazaartest.framework.presentation.list.ListFragment
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@FlowPreview
@Singleton
@Component(modules = [AppModule::class , FragmentFactoryModule::class,ProductionModule::class , ViewModelModule::class])
interface AppComponent : AndroidInjector<BaseApplication?> {



    @Component.Factory
    interface Factory{

        fun create(@BindsInstance app: BaseApplication): AppComponent
    }
     fun inject(mainActivity: MainActivity)
    fun inject(listFragment: ListFragment)
    fun inject(detailFragment: DetailFragment)
}