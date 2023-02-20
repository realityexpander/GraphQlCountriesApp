package com.realityexpander.graphqlcountriesapp.domain

class GetCountryUseCase(
    private val countryClient: ICountryClient
) {

    suspend fun execute(code: String): DetailedCountry? {
        return countryClient.getCountry(code)
    }
}