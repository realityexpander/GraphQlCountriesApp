package com.realityexpander.graphqlcountriesapp.data

import com.apollographql.apollo3.ApolloClient
import com.realityexpander.CountriesQuery
import com.realityexpander.CountryQuery
import com.realityexpander.graphqlcountriesapp.domain.CountryClient
import com.realityexpander.graphqlcountriesapp.domain.DetailedCountry
import com.realityexpander.graphqlcountriesapp.domain.SimpleCountry

class ApolloCountryClient(
    private val apolloClient: ApolloClient
): CountryClient {

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