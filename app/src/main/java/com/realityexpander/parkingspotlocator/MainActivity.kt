package com.realityexpander.parkingspotlocator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.realityexpander.parkingspotlocator.presentation.MapScreen
import com.realityexpander.parkingspotlocator.ui.theme.MapComposeGuideTheme
import dagger.hilt.android.AndroidEntryPoint

// GCP Control panel
// https://console.cloud.google.com/google/maps-apis/credentials?authuser=2&project=parking-spot-locator-1

// Snazzy maps for fallout map
// https://snazzymaps.com/style/87718/fallout-pip-boy

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
