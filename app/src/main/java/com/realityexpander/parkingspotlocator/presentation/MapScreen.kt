package com.realityexpander.parkingspotlocator.presentation

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ToggleOff
import androidx.compose.material.icons.filled.ToggleOn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import androidx.lifecycle.viewmodel.compose.viewModel // not the same as Android's viewModel

@Composable
fun MapScreen(
    viewModel: MapViewModel = viewModel(),
) {
    val scaffoldState = rememberScaffoldState()
    val uiSettings = remember { MapUiSettings( zoomControlsEnabled = false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val localContext = LocalContext.current

    // If there are user messages to show on the screen,
    // show the first one and notify the ViewModel.
    viewModel.state.userMessages.firstOrNull()?.let { userMessage ->
        println(userMessage.id)
        LaunchedEffect(userMessage.id) {
            snackbarHostState.showSnackbar(userMessage.message, duration = SnackbarDuration.Short)
            // Once the message is displayed and dismissed, notify the ViewModel.
            viewModel.onEvent(MapEvent.ClearUserMessage(userMessage.id))
        }
    }

    Scaffold(
        scaffoldState = rememberScaffoldState(snackbarHostState = snackbarHostState),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.onEvent(MapEvent.ToggleFalloutMap)
                }
            ) {
                Icon(
                    imageVector = if (viewModel.state.isFalloutMapVisible) {
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
            onMapLongClick = { latlng ->
                viewModel.onEvent(MapEvent.MapLongClick(latlng))
                // Toast.makeText(localContext, "Adding spot @ $latlng", Toast.LENGTH_SHORT).show()
            },
        )
    }
}