package com.nramos.internationalbusinessmen.di

import com.nramos.data.repositories.RatesRepositoryImpl
import com.nramos.data.repositories.TransactionsRepositoryImpl
import com.nramos.domain.repositories.RatesRepository
import com.nramos.domain.repositories.TransactionsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class DataRepositoriesModule {

    @Binds
    internal abstract fun bindRatesRepository(impl: RatesRepositoryImpl): RatesRepository

    @Binds
    internal abstract fun bindTransactionsRepository(impl: TransactionsRepositoryImpl): TransactionsRepository

}