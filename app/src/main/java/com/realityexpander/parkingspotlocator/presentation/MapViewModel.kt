package com.realityexpander.parkingspotlocator.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.MapProperties

class MapViewModel : ViewModel() {

    var state by mutableStateOf(MapState())

    fun onEvent(event: MapEvent) {
        when (event) {
            is MapEvent.MapLongClick -> onMapAddOrRemoveParkingLocation(event.latLng)
            MapEvent.ToggleFalloutMap -> {
                state =
                    state.copy(
                        isFalloutMapVisible = !state.isFalloutMapVisible,
                        mapProperties = state.mapProperties.copy(
                            mapStyleOptions = if (state.isFalloutMapVisible) {
                                null  // deactivate the custom map style
                            } else
                                MapStyleOptions(MapStyle.json) // activate the custom map style
                        )
                    )
            }
        }
    }

    private fun onMapAddOrRemoveParkingLocation(parkingLocation: LatLng) {
        TODO("Not yet implemented")
    }
}