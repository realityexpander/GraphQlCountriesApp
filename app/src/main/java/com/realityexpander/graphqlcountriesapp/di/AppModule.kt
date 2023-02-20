package com.realityexpander.graphqlcountriesapp.di

import com.apollographql.apollo3.ApolloClient
import com.realityexpander.graphqlcountriesapp.data.CountryClientApolloImpl
import com.realityexpander.graphqlcountriesapp.domain.ICountryClient
import com.realityexpander.graphqlcountriesapp.domain.GetCountriesUseCase
import com.realityexpander.graphqlcountriesapp.domain.GetCountryUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApolloClient(): ApolloClient {
        return ApolloClient.Builder()
            .serverUrl("https://countries.trevorblades.com/graphql")
            .build()
    }

    @Provides
    @Singleton
    fun provideCountryClient(apolloClient: ApolloClient): ICountryClient {
        return CountryClientApolloImpl(apolloClient)
    }

    @Provides
    @Singleton
    fun provideGetCountriesUseCase(countryClient: ICountryClient): GetCountriesUseCase {
        return GetCountriesUseCase(countryClient)
    }

    @Provides
    @Singleton
    fun provideGetCountryUseCase(countryClient: ICountryClient): GetCountryUseCase {
        return GetCountryUseCase(countryClient)
    }
}