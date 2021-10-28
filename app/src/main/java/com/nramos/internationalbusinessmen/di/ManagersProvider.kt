package com.nramos.internationalbusinessmen.di

import com.nramos.domain.managers.CurrencyManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ManagersProvider {

    @Singleton
    @Provides
    fun provideCurrencyManager(): CurrencyManager = CurrencyManager()

}