package com.realityexpander.graphqlcountriesapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.realityexpander.graphqlcountriesapp.domain.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountriesViewModel @Inject constructor(
    private val getCountriesUseCase: GetCountriesUseCase,
    private val getCountryUseCase: GetCountryUseCase,
    private val countryClient: ICountryClient
): ViewModel() {

    private val _state = MutableStateFlow(CountriesState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.update { it.copy(
                isLoading = true
            ) }
            _state.update { it.copy(
//                countries = getCountriesUseCase.execute(), // remove use case implementation
                countries = try {
                        countryClient.getCountries()
                            .sortedBy { it.name }
                    } catch(e:Exception) {
                        println("Error: $e")
                        listOf(SimpleCountry("ERROR", e.localizedMessage, "⚠️", ""))
                    },
                isLoading = false
            ) }
        }
    }

    fun selectCountry(code: String) {
        viewModelScope.launch {
            _state.update { it.copy(
//                selectedCountry = getCountryUseCase.execute(code) // remove use case implementation
                selectedCountry = try {
                        countryClient.getCountry(code)
                    } catch(e:Exception) {
                        println("Error: $e")
                        DetailedCountry("ERROR", e.localizedMessage, "⚠️", "", "", emptyList(), "")
                    }
            ) }
        }
    }

    fun dismissCountryDialog() {
        _state.update { it.copy(
            selectedCountry = null
        ) }
    }

    data class CountriesState(
        val countries: List<SimpleCountry> = emptyList(),
        val isLoading: Boolean = false,
        val selectedCountry: DetailedCountry? = null
    )
}