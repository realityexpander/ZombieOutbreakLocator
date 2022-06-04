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
                    onAddOrRemoveParkingMarker(event.latLng)
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
        }
    }

    // Can pass in a map LatLng or id of a parking marker or both.
    // If adding a new marker, the id will be generated and mapLatLng must be non-null.
    // If removing a marker, the id will be the id of the marker to remove.
    private fun onAddOrRemoveParkingMarker(mapLatLng: LatLng? = null, id: Long? = null) {
        if (mapLatLng == null && id == null) return

        var actionPerformed = ""

        // Check if the marker location is already in the list
        val (parkingMarkerByLocation, isMatchLocation) =
            if (mapLatLng != null) {
                state.parkingMarkers.firstOrNullMatchLatLng(mapLatLng)
            } else {
                ParkingMarkerIsMatch(null, false)
            }

        // Check if the marker id is already in the list
        val (parkingMarkerById, isMatchId) =
            if (id != null) {
                state.parkingMarkers.firstOrNullMatchId(id)
            } else {
                ParkingMarkerIsMatch(null, false)
            }

        // Add or remove the marker
        val newParkingMarkers =
            if (isMatchLocation) {
                actionPerformed = "Removed a zombie location"

                state.parkingMarkers.removeMarkerByLatLng(mapLatLng)
            } else if (isMatchId) {
                actionPerformed = "Removed a zombie location"

                state.parkingMarkers.removeMarkerById(parkingMarkerById!!.id)
            } else {
                actionPerformed = "Added a zombie location"
                lastId++

                state.parkingMarkers + ParkingMarker(
                    id = lastId,
                    lat = mapLatLng?.latitude ?: throw IllegalArgumentException("mapLatLng is null"),
                    lng = mapLatLng.longitude,
                )
            }

        state = state.copy(parkingMarkers = newParkingMarkers)
        addUserMessage("$actionPerformed: $lastId")
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


    private fun List<ParkingMarker>.firstOrNullMatchLatLng(
        mapLatLng: LatLng?
    ): ParkingMarkerIsMatch {
        if(mapLatLng == null) {
            return ParkingMarkerIsMatch(null, false)
        }

        val parkingMarker = firstOrNull {
            it.lat == mapLatLng.latitude && it.lng == mapLatLng.longitude
        }

        return if (parkingMarker != null) {
            ParkingMarkerIsMatch(parkingMarker, true)
        } else {
            ParkingMarkerIsMatch(null, false)
        }
    }

    private fun List<ParkingMarker>.firstOrNullMatchId(id: Long?): ParkingMarkerIsMatch {
        if(id == null) {
            return ParkingMarkerIsMatch(null, false)
        }

        val parkingMarker = firstOrNull {
            it.id == id
        }

        return if (parkingMarker != null) {
            ParkingMarkerIsMatch(parkingMarker, true)
        } else {
            ParkingMarkerIsMatch(null, false)
        }
    }

    private fun List<ParkingMarker>.removeMarkerByLatLng(removeLatLng: LatLng?): List<ParkingMarker> {
        if (removeLatLng == null) return this

        return this.filter { (lat, lng) ->
            (lat == removeLatLng.latitude) && (lng == removeLatLng.longitude)
        }
    }

    private fun List<ParkingMarker>.removeMarkerById(id: Long): List<ParkingMarker> {
        return this.filterNot { it.id == id }
    }


    data class ParkingMarkerIsMatch(
        val parkingMarker: ParkingMarker?,
        val isMatch: Boolean
    )

    private infix fun ParkingMarker?.to(isMatch: Boolean) =
        ParkingMarkerIsMatch(this, isMatch)


}
