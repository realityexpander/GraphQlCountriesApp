package com.realityexpander.graphqlcountriesapp.domain

class GetCountriesUseCase(
    private val countryClient: ICountryClient
) {

    suspend fun execute(): List<SimpleCountry> {
        return countryClient
            .getCountries()
            .sortedBy { it.name }
    }
}