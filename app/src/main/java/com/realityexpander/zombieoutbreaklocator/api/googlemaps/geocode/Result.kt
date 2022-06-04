package com.realityexpander.zombieoutbreaklocator.api.googlemaps.geocode
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Result(
    @SerialName("address_components")
    val addressComponents: List<AddressComponent>,
    @SerialName("formatted_address")
    val formattedAddress: String,
    @SerialName("geometry")
    val geometry: Geometry,
    @SerialName("place_id")
    val placeId: String,
    @SerialName("plus_code")
    val plusCode: PlusCodeX? = null,
    @SerialName("types")
    val types: List<String>
)