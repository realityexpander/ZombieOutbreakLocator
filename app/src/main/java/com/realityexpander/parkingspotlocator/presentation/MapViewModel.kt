package com.realityexpander.parkingspotlocator.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.MapProperties
import com.realityexpander.parkingspotlocator.domain.model.ParkingMarker
import com.realityexpander.parkingspotlocator.domain.repository.ParkingMarkerRepository
import com.realityexpander.parkingspotlocator.mappers.toParkingMarkerEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val parkingMarkerRepo: ParkingMarkerRepository
) : ViewModel() {

    var state by mutableStateOf(MapState())
        private set

    var lastId: Long = 0

    init {
        viewModelScope.launch {
            parkingMarkerRepo.getParkingMarkers().collectLatest { markers ->
                state = state.copy(
                    parkingMarkers = markers,
                    mapProperties = MapProperties(
                        mapStyleOptions = MapStyleOptions(MapStyle.json)
                    )
                )
            }
        }

    }

    fun onEvent(event: MapEvent) {
        when (event) {
            is MapEvent.OnMapLongClick -> {
                try {
                    addParkingMarker(event.latLng)
                } catch (e: Exception) {
                    e.printStackTrace()
                    addUserMessage("Error adding marker")
                }
            }
            is MapEvent.OnToggleFalloutMap -> {
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
            is MapEvent.OnRemoveUserMessage -> {
                removeUserMessage(event.userMessageId)
            }
            is MapEvent.OnInfoWindowLongClick -> {
                onRemoveParkingMarker(id = event.parkingMarkerId)
            }
        }
    }

    private fun addParkingMarker(mapLatLng: LatLng) {
        val parkingMarker = ParkingMarker(
            lat = mapLatLng.latitude,
            lng = mapLatLng.longitude
        )
        viewModelScope.launch {
            parkingMarkerRepo.insertParkingMarker(parkingMarker)
        }
    }

    // Can pass in a map LatLng or id of a marker or both.
    // If removing a marker, the id will be the id of the marker to remove.
    private fun onRemoveParkingMarker(mapLatLng: LatLng? = null, id: Long? = null) {
        if (mapLatLng == null && id == null) return

        // Check if the marker location or id is already in the list
        val markerToRemove: ParkingMarker? =
            state.parkingMarkers.firstOrNullMatchLatLng(mapLatLng) ?:
            state.parkingMarkers.firstOrNullMatchId(id)

        markerToRemove?.let {
            viewModelScope.launch {
                parkingMarkerRepo.deleteParkingMarker(markerToRemove)
            }
            addUserMessage("Removed zombie location: id=${markerToRemove.id}")
        } ?: run {
            addUserMessage("Could not find marker to remove")
        }
    }

    private fun addUserMessage(message: String) {
        val messages = state.userMessages + UserMessage(
            id = UUID.randomUUID().mostSignificantBits,
            message = message
        )
        state = state.copy(userMessages = messages)
    }

    private fun removeUserMessage(messageId: Long) {
        val messages = state.userMessages.filterNot { it.id == messageId }
        state = state.copy(userMessages = messages)
    }

    private fun List<ParkingMarker>.firstOrNullMatchLatLng(mapLatLng: LatLng?): ParkingMarker? {
        if (mapLatLng == null) return null

        return firstOrNull { marker ->
            marker.lat == mapLatLng.latitude && marker.lng == mapLatLng.longitude
        }
    }

    private fun List<ParkingMarker>.firstOrNullMatchId(id: Long?): ParkingMarker? {
        if (id == null) return null

        return firstOrNull { marker ->
            marker.id == id
        }
    }

}
