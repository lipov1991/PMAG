package pl.lipov.laborki.common.utils

import android.content.Context
import android.widget.Toast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*

class MapUtils {

    private val galleryLatLng = LatLng(52.2550, 21.0378)
    lateinit var markerGallery : Marker

    fun setUpMap(
        googleMap: GoogleMap,
        context: Context
    ) {

        // Construct a CameraPosition focusing on Mountain View and animate the camera to that position.
        val cameraPosition = CameraPosition.Builder()
            .target(galleryLatLng) // Sets the center of the map to Mountain View
            .zoom(18f)            // Sets the zoom
            //.bearing(90f)         // Sets the orientation of the camera to east (orentacja kamery na wschód)
            //.tilt(30f)            // Sets the tilt of the camera to 30 degrees (kąt nachylenia kamery)
            .build()              // Creates a CameraPosition from the builder
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))

        markerGallery = googleMap.addMarker(
            MarkerOptions()
                .position(galleryLatLng)
                .title("Galeria Wileńska")
        )

//        googleMap.setOnIndoorStateChangeListener(object: GoogleMap.OnIndoorStateChangeListener{
//            override fun onIndoorBuildingFocused() {
//                context.showToast("onIndoorBuildingFocused")
//
//            }
//
//            override fun onIndoorLevelActivated(
//                    indoorBuilding: IndoorBuilding?
//            ) {
//                context.showToast("onIndoorLevelAcvtivated")
//            }
//
//        })


    }

    private fun Context.showToast(
            msg: String
    ){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
    }

}