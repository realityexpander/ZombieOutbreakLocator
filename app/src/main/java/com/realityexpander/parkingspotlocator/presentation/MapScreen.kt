package com.realityexpander.parkingspotlocator.presentation

import android.content.Context
import android.graphics.Bitmap
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Column
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
import androidx.core.content.ContextCompat
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import androidx.lifecycle.viewmodel.compose.viewModel // not the same as Android's viewModel
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.Marker
import com.realityexpander.parkingspotlocator.R

@Composable
fun MapScreen(
    viewModel: MapViewModel = viewModel(), // must use compose.viewModel!
) {
//    val scaffoldState = rememberScaffoldState()
    val uiSettings = remember { MapUiSettings(zoomControlsEnabled = false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val localContext = LocalContext.current // for Toast

    // If there are user messages to show on the screen,
    // show the first one and notify the ViewModel.
    viewModel.state.userMessages.firstOrNull()?.let { userMessage ->
        println(userMessage.id)
        LaunchedEffect(userMessage.id) {
            snackbarHostState.showSnackbar(userMessage.message, duration = SnackbarDuration.Short)
            // Once the message is displayed and dismissed, notify the ViewModel.
            viewModel.onEvent(MapEvent.OnRemoveUserMessage(userMessage.id))
        }
    }

    Scaffold(
        scaffoldState = rememberScaffoldState(snackbarHostState = snackbarHostState),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.onEvent(MapEvent.OnToggleFalloutMap)
                }
            ) {
                Icon(
                    imageVector = if (viewModel.state.isFalloutMapVisible) {
                        Icons.Filled.ToggleOn
                    } else {
                        Icons.Filled.ToggleOff
                    },
                    contentDescription = "Toggle Fallout Style"
                )
            }
        }
    ) {
        Column() {
            Text(
                "Long tap marks a zombie outbreak 🧟‍ ☣️",
                style = MaterialTheme.typography.h6
            )

            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                properties = viewModel.state.mapProperties,
                uiSettings = uiSettings,
                onMapLongClick = { latlng ->
                    viewModel.onEvent(MapEvent.OnMapLongClick(latlng))
                    // Toast.makeText(localContext, "Adding spot @ $latlng", Toast.LENGTH_SHORT).show()
                },
            ) {
                viewModel.state.parkingMarkers.forEach { marker ->
                    Marker(
                        position = LatLng(marker.lat ?: 0.0, marker.lng ?: 0.0),
                        title = "Zombie outbreak " +
                                "id:${marker.id} " +
                                "(${marker.lat.toString().dropLast(11)}, " +
                                "${marker.lng.toString().dropLast(11)})",
                        snippet = "Long tap to delete",
                        onInfoWindowLongClick = {
                            it.hideInfoWindow()
                            viewModel.onEvent(
                                MapEvent.OnInfoWindowLongClick(marker.id)
                            )
                        },
                        onClick = {
                            it.showInfoWindow()
                            true
                        },
//                        icon = BitmapDescriptorFactory.defaultMarker(
//                            BitmapDescriptorFactory.HUE_GREEN
//                        ),
                        icon = bitmapDescriptor(
                            context = localContext,
                            vectorResId = R.drawable.ic_zombieoutbreak_foreground
                        )
                    )
                }
            }
        }

    }
}

@Composable
fun MapMarker(
    context: Context,
    position: LatLng,
    title: String,
    snippet: String,
    @DrawableRes iconResourceId: Int
) {
    val icon = bitmapDescriptor(
        context, iconResourceId
    )
    Marker(
        position = position,
        title = title,
        snippet = snippet,
        icon = icon,
    )
}

fun bitmapDescriptor(
    context: Context,
    @DrawableRes vectorResId: Int
): BitmapDescriptor? {

    // retrieve the actual drawable
    val drawable = ContextCompat.getDrawable(context, vectorResId) ?: return null
    drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
    val bm = Bitmap.createBitmap(
        drawable.intrinsicWidth,
        drawable.intrinsicHeight,
        Bitmap.Config.ARGB_8888
    )

    // draw it onto the bitmap
    val canvas = android.graphics.Canvas(bm)
    drawable.draw(canvas)
    return BitmapDescriptorFactory.fromBitmap(bm)
}