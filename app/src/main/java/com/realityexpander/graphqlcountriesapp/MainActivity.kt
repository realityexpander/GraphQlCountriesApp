package com.realityexpander.graphqlcountriesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.realityexpander.graphqlcountriesapp.presentation.CountriesScreen
import com.realityexpander.graphqlcountriesapp.presentation.CountriesViewModel
import com.realityexpander.graphqlcountriesapp.ui.theme.GraphQlCountriesAppTheme
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

// Source data:
// https://studio.apollographql.com/public/countries/home/?variant=current

// Must run this to download the graphql schema:
// ./gradlew :app:downloadApolloSchema --endpoint='https://countries.trevorblades.com/graphql' --schema=app/src/main/graphql/com/realityexpander/schema.graphqls

// Studio (to try queries):
// https://studio.apollographql.com/public/countries/explorer?variant=current

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GraphQlCountriesAppTheme {
                val viewModel = hiltViewModel<CountriesViewModel>()
                val state by viewModel.state.collectAsState()

                CountriesScreen(
                    state = state,
                    onSelectCountry = viewModel::selectCountry,
                    onDismissCountryDialog = viewModel::dismissCountryDialog
                )
            }
        }
    }
}