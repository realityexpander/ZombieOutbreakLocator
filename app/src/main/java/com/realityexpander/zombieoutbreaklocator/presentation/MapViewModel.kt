package com.realityexpander.zombieoutbreaklocator.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.MapProperties
import com.realityexpander.zombieoutbreaklocator.api.googlemaps.geocode.GoogleMapsGeocode
import com.realityexpander.zombieoutbreaklocator.domain.model.ZombieMarker
import com.realityexpander.zombieoutbreaklocator.domain.repository.ZombieMarkerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.net.URL
import java.util.*
import javax.inject.Inject

private val jsonLenientIgnoreUnknown = Json {
    isLenient = true
    ignoreUnknownKeys = true
}

@HiltViewModel
class MapViewModel @Inject constructor(
    private val zombieMarkerRepo: ZombieMarkerRepository
) : ViewModel() {

    var state by mutableStateOf(MapState())
        private set

    var lastId: Long = 0

    init {
        viewModelScope.launch {
            zombieMarkerRepo.getZombieMarkers().collectLatest { markers ->
                state = state.copy(
                    zombieMarkers = markers,
                    mapProperties = MapProperties(
                        mapStyleOptions = MapStyleOptions(MapStyleBlue.json)
                    )
                )
            }
        }

    }

    fun onEvent(event: MapEvent) {
        when (event) {
            is MapEvent.OnMapLongClick -> {
                try {
                    addZombieMarker(event.latLng)
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
                                MapStyleOptions(MapStyleBlue.json) // activate the custom map style
                        ),
                        isFalloutMapVisible = !state.isFalloutMapVisible,
                    )
            }
            is MapEvent.OnRemoveUserMessage -> {
                removeUserMessage(event.userMessageId)
            }
            is MapEvent.OnInfoWindowLongClick -> {
                onRemoveZombieMarker(id = event.zombieMarkerId)
            }
        }
    }


    private fun addZombieMarker(mapLatLng: LatLng) {
        val zombieMarker = ZombieMarker(
            lat = mapLatLng.latitude,
            lng = mapLatLng.longitude
        )
        viewModelScope.launch {
            zombieMarkerRepo.insertZombieMarker(zombieMarker)

            withContext(Dispatchers.IO) {
                val response =
                    URL(
                        "https://maps.googleapis.com/maps/api/geocode/json?latlng=" +
                                "${mapLatLng.latitude}" +
                                ",${mapLatLng.longitude}&key=AIzaSyCfBxP3Ba7CfpuKX6bz62pJYFhhLgD45Vk"
                    ).readText()
                println(response)

                val result = jsonLenientIgnoreUnknown
                    .decodeFromString<GoogleMapsGeocode>(response)

                val city = result.results[0].addressComponents.find {
                    it.types.contains("locality")
                }?.longName ?: result.results[0].addressComponents.find {
                    it.types.contains("administrative_area_level_1")
                }?.longName ?: result.results[0].addressComponents.find {
                    it.types.contains("sublocality")
                }?.longName ?: "Unknown"

                val country = result.results[0].addressComponents.find {
                    it.types.contains("country")
                }?.longName ?: "Unknown"

                addUserMessage("Outbreak in $city, $country")

            }
        }
    }

    // Can pass in a map LatLng or id of a marker or both.
    // If removing a marker, the id will be the id of the marker to remove.
    private fun onRemoveZombieMarker(mapLatLng: LatLng? = null, id: Long? = null) {
        if (mapLatLng == null && id == null) return

        // Check if the marker location or id is already in the list
        val markerToRemove: ZombieMarker? =
            state.zombieMarkers.firstOrNullMatchLatLng(mapLatLng)
                ?: state.zombieMarkers.firstOrNullMatchId(id)

        markerToRemove?.let {
            viewModelScope.launch {
                zombieMarkerRepo.deleteZombieMarker(markerToRemove)
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

    private fun List<ZombieMarker>.firstOrNullMatchLatLng(mapLatLng: LatLng?): ZombieMarker? {
        if (mapLatLng == null) return null

        return firstOrNull { marker ->
            marker.lat == mapLatLng.latitude && marker.lng == mapLatLng.longitude
        }
    }

    private fun List<ZombieMarker>.firstOrNullMatchId(id: Long?): ZombieMarker? {
        if (id == null) return null

        return firstOrNull { marker ->
            marker.id == id
        }
    }

}
