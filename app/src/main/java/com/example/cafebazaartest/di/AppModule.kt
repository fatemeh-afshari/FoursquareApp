package com.example.cafebazaartest.di

import android.content.SharedPreferences
import androidx.room.Database
import com.example.cafebazaartest.business.data.cache.abstraction.CacheDataSource
import com.example.cafebazaartest.business.data.cache.implementation.CacheDataSourceImpl
import com.example.cafebazaartest.business.data.network.abstraction.NetworkDataSource
import com.example.cafebazaartest.business.data.network.implementation.NetworkDataSourceImpl
import com.example.cafebazaartest.business.interactors.venuedetail.DetailInteractors
import com.example.cafebazaartest.business.interactors.venuedetail.GetVenueDetail
import com.example.cafebazaartest.business.interactors.venuelist.*
import com.example.cafebazaartest.framework.datasource.cache.abstraction.DaoService
import com.example.cafebazaartest.framework.datasource.cache.database.Dao
import com.example.cafebazaartest.framework.datasource.cache.database.VenueDatabase
import com.example.cafebazaartest.framework.datasource.cache.implementation.DaoServiceImpl
import com.example.cafebazaartest.framework.datasource.cache.mappers.CacheMapper
import com.example.cafebazaartest.framework.datasource.network.abstraction.NetworkService
import com.example.cafebazaartest.framework.datasource.network.api.Api
import com.example.cafebazaartest.framework.datasource.network.implementation.NetworkServiceImpl
import com.example.cafebazaartest.framework.presentation.BaseApplication
import com.example.cafebazaartest.util.BASE_URL
import com.example.cafebazaartest.util.SharedPreferencesHelper
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@FlowPreview
@Module
object AppModule {

    @JvmStatic
    @Singleton
    @Provides
    fun provideRetrofitInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideApi(retrofit: Retrofit): Api {
        return retrofit.create<Api>(Api::class.java)
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideSharedPrefs(
        application: BaseApplication
    ): SharedPreferencesHelper {
        return SharedPreferencesHelper(application)
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideDAO(venueDatabase: VenueDatabase): Dao {
        return venueDatabase.dao()
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideCacheMapper(): CacheMapper {
        return CacheMapper()
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideDaoService(
        dao: Dao,
        venueMapper: CacheMapper
    ): DaoService {
        return DaoServiceImpl(dao, venueMapper)
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideCacheDataSource(
        daoService: DaoService
    ): CacheDataSource {
        return CacheDataSourceImpl(daoService)
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideNetworkService(
        api: Api
    ): NetworkService {
        return NetworkServiceImpl(
            api
        )
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideNetworkDataSource(
        networkService: NetworkServiceImpl
    ): NetworkDataSource {
        return NetworkDataSourceImpl(
            networkService
        )
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideDetailInteractors(
        networkDataSource: NetworkDataSource
    ): DetailInteractors {
        return DetailInteractors(
            GetVenueDetail(networkDataSource)
        )
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideListInteractors(
        networkDataSource: NetworkDataSource,
        cacheDataSource: CacheDataSource
    ): ListInteractors {
        return ListInteractors(
            CleanCache(cacheDataSource),
            GetVenueListFromCache(cacheDataSource),
            GetVenueListFromNetwork(networkDataSource, cacheDataSource),
            AddVenueListToCache()
        )
    }


}