package com.realityexpander.parkingspotlocator.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ToggleOff
import androidx.compose.material.icons.filled.ToggleOn
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.MapView
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings

@Composable
fun MapScreen(
    viewModel: MapViewModel = MapViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val uiSettings = remember { MapUiSettings() }

    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    // viewModel.onFabClick()
                }
            ) {
                Icon(
                    imageVector = if (viewModel.state.isFalloutMap) {
                        Icons.Filled.ToggleOff
                    } else {
                        Icons.Filled.ToggleOn
                    },
                    contentDescription = "Toggle Fallout Style"
                )
            }
        }
    ) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            properties = viewModel.state.mapProperties,
            uiSettings = uiSettings,
            onMapLongClick = {
                viewModel.onMapLongClick(it)
            },
        )
    }
}