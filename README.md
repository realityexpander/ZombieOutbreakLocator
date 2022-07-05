# Zombie Outbreak Locator

Zombie Outbreak Locator app using MVVM, Compose, Google Maps Composable, Custom Maps, Custom Icons, Room, Flow, Coroutines, GeoCode API

[<img src="https://user-images.githubusercontent.com/5157474/172029910-e6c7fc81-7bf6-48e3-954c-4dc7ed8ef5cf.png" width="325"/>](https://user-images.githubusercontent.com/5157474/172029910-e6c7fc81-7bf6-48e3-954c-4dc7ed8ef5cf.png)
[<img src="https://user-images.githubusercontent.com/5157474/172029917-787daa35-8fc8-41da-a811-6db635fe36df.png" width="325"/>](https://user-images.githubusercontent.com/5157474/172029917-787daa35-8fc8-41da-a811-6db635fe36df.png)
[<img src="https://user-images.githubusercontent.com/5157474/172029922-b5f6a748-4dc7-4d82-bd3f-dab8f2dbaeef.png" width="325"/>](https://user-images.githubusercontent.com/5157474/172029922-b5f6a748-4dc7-4d82-bd3f-dab8f2dbaeef.png)

- Allows users to add & delete Zombie Outbreak locations on Google Maps
- User selectable custom color scheme for map

## ---- Tech used ----

- List of marked zombie outbreak locations is cached locally in a `Room` database
- Uses custom query for `Room` database
- Shows proper use of `Dagger-Hilt` & SOLID architecture
- Uses compose for view layer, `ViewModel` and `mutableState` to send UI events
- Uses Resource sealed class to handle errors and messaging
- Custom GeoCoding parsers using `Google Maps Geocoder` for Country name and city location
- Custom Icons

To install the Apk:

1. Open this link on your Android device:
   https://github.com/realityexpander/ZombieOutbreakLocator/blob/master/ZombieOutbreak_1.0.apk
2. Tap the "skewer" menu and tap the "download"

   [![](https://user-images.githubusercontent.com/5157474/147434050-57102a30-af32-46ed-a90b-d94e0c4a4f35.jpg)]()
3. Allow the file to download (DO NOT click "show details")
4. After the file is downloaded, click "OK" to install
5. Click "OK" to install
6. Click "OK" to launch

If you have developer options turned on, you may need to turn off "USB Debugging" if the "Waiting for debugger" dialog is displayed.
