package com.realityexpander.zombieoutbreaklocator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.realityexpander.zombieoutbreaklocator.presentation.MapScreen
import com.realityexpander.zombieoutbreaklocator.ui.theme.MapComposeGuideTheme
import dagger.hilt.android.AndroidEntryPoint

// GCP Control panel
// https://console.cloud.google.com/google/maps-apis/credentials?authuser=2&project=parking-spot-locator-1

// Snazzy maps for fallout map
// https://snazzymaps.com/style/87718/fallout-pip-boy

// Use local.properties for secrets
// Creates Build.Config and AndroidManifest.xml values simultaneously
// https://github.com/google/secrets-gradle-plugin

// Android maps with Compose
// https://github.com/googlemaps/android-maps-compose

// Kotlinx Serialization library
// https://github.com/Kotlin/kotlinx.serialization
// https://www.raywenderlich.com/26883403-android-data-serialization-tutorial-with-the-kotlin-serialization-library

// Simple fetch with URL
// https://en.proft.me/2018/04/19/how-fetch-data-over-network-kotlin-and-android/

// Reverse Geocoding
// https://developers.google.com/maps/documentation/geocoding/start

// Google maps samples
// https://github.com/googlemaps/android-samples/tree/main/ApiDemos/kotlin/app/src/gms/java/com/example/kotlindemos

// Custom Icons for Compose Google Maps
// https://towardsdev.com/jetpack-compose-custom-google-map-marker-erselan-khan-e6e04178a30b
// https://github.com/arsalankhan994/jetpack-compose-step-by-step/blob/screen-google-map-custom-marker/app/src/main/java/com/erselankhan/compose/ui/screen/MapScreenWithCustomMarker.kt



@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MapComposeGuideTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MapScreen()
                }
            }
        }
    }
}
