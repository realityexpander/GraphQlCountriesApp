package com.realityexpander.graphqlcountriesapp.data

import com.apollographql.apollo3.ApolloClient
import com.realityexpander.CountriesQuery
import com.realityexpander.CountryQuery
import com.realityexpander.graphqlcountriesapp.domain.DetailedCountry
import com.realityexpander.graphqlcountriesapp.domain.ICountryClient
import com.realityexpander.graphqlcountriesapp.domain.SimpleCountry

class CountryClientApolloImpl(
    private val apolloClient: ApolloClient
): ICountryClient {

    override suspend fun getCountries(): List<SimpleCountry> {
        return apolloClient
            .query(CountriesQuery())
            .execute()
            .data
            ?.countries
            ?.map { it.toSimpleCountry() }
            ?: emptyList()
    }

    override suspend fun getCountry(code: String): DetailedCountry? {
        return apolloClient
            .query(CountryQuery(code))
            .execute()
            .data
            ?.country
            ?.toDetailedCountry()
    }
}