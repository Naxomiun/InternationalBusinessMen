package com.nramos.internationalbusinessmen.di

import com.nramos.data.services.RatesService
import com.nramos.data.services.TransactionsService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataServicesProvider {

    @Provides
    @Singleton
    fun provideRatesService(retrofit: Retrofit) : RatesService = retrofit.create(RatesService::class.java)

    @Provides
    @Singleton
    fun provideTransactionService(retrofit: Retrofit) : TransactionsService = retrofit.create(TransactionsService::class.java)

}
