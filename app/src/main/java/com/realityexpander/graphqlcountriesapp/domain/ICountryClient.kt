package com.realityexpander.graphqlcountriesapp.domain

interface ICountryClient {
    suspend fun getCountries(): List<SimpleCountry>
    suspend fun getCountry(code: String): DetailedCountry?
}