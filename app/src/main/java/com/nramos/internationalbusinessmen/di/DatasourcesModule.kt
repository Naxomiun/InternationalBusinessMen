package com.nramos.internationalbusinessmen.di

import com.nramos.data.datasources.RatesRemoteDatasourceImpl
import com.nramos.data.datasources.TransactionsRemoteDatasourceImpl
import com.nramos.domain.datasources.RatesRemoteDatasource
import com.nramos.domain.datasources.TransactionsRemoteDatasource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DatasourcesModule {

    @Binds
    internal abstract fun bindRemoteRateDatasource(impl: RatesRemoteDatasourceImpl): RatesRemoteDatasource

    @Binds
    internal abstract fun bindRemoteTransactionDatasource(impl: TransactionsRemoteDatasourceImpl): TransactionsRemoteDatasource

}