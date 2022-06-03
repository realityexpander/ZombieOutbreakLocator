package com.realityexpander.parkingspotlocator.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.realityexpander.parkingspotlocator.data.ParkingMarkerRepositoryImpl
import com.realityexpander.parkingspotlocator.domain.model.ParkingMarker
import java.util.*

class MapViewModel(
    private val parkingMarkerRepositoryImpl: ParkingMarkerRepositoryImpl
) : ViewModel() {

    var state by mutableStateOf(MapState())
        private set

    var lastId: Long = 0

    fun onEvent(event: MapEvent) {
        when (event) {
            is MapEvent.MapLongClick -> onMapAddOrRemoveParkingLocation(event.latLng)
            is MapEvent.ToggleFalloutMap -> {
                state =
                    state.copy(
                        mapProperties = state.mapProperties.copy(
                            mapStyleOptions = if (state.isFalloutMapVisible) {
                                null  // deactivate the custom map style
                            } else
                                MapStyleOptions(MapStyle.json) // activate the custom map style
                        ),
                        isFalloutMapVisible = !state.isFalloutMapVisible,
                    )
            }
            is MapEvent.HideUserMessage -> {
                hideUserMessage(event.userMessageId)
            }
        }
    }

    private fun onMapAddOrRemoveParkingLocation(parkingLocation: LatLng) {
        var actionDone = "Added a new parking location"

        val newParkingMarkers =
            if (state.parkingMarkers.containsLocation(
                    parkingLocation.latitude,
                    parkingLocation.longitude
                )
            ) {
                actionDone = "Removed a parking location"
                state.parkingMarkers.removeLocationByLatLng(
                    parkingLocation.latitude,
                    parkingLocation.longitude
                )
            } else {
                state.parkingMarkers + ParkingMarker(
                    id = lastId++,
                    lat = parkingLocation.latitude,
                    lng = parkingLocation.longitude
                )
            }
        state = state.copy(parkingMarkers = newParkingMarkers)
        showUserMessage("$actionDone:$lastId")
    }

    private fun showUserMessage(message: String) {
        val messages = state.userMessages + UserMessage(
            id = UUID.randomUUID().mostSignificantBits,
            message = message
        )
        state = state.copy(userMessages = messages)
    }

    private fun hideUserMessage(messageId: Long) {
        val messages = state.userMessages.filterNot { it.id == messageId }
        state = state.copy(userMessages = messages)
    }

    private fun List<ParkingMarker>.containsLocation(
        latitude: Double,
        longitude: Double
    ): Boolean {
        return this.any { (lat, lng) ->
            lat == latitude && lng == longitude
        }
    }

    private fun List<ParkingMarker>.removeLocationByLatLng(
        removeLatitude: Double,
        removeLongitude: Double
    ): List<ParkingMarker> {
        return this.filter { (lat, lng) ->
            lat == removeLatitude && lng == removeLongitude
        }
    }
}
