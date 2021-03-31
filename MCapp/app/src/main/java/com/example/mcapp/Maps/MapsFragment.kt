package com.example.mcapp.Maps

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mcapp.MyCameraX.CameraPermissionsFragment
import com.example.mcapp.MyCameraX.CameraPermissionsFragment.Companion.PERMISSIONS_REQUEST_CAMERA_CODE
import com.example.mcapp.MyCameraX.CameraPermissionsFragment.Companion.PERMISSIONS_REQUIRED_CAMERA

import com.example.mcapp.R

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_maps.view.*

class MapsFragment : Fragment(), OnMapReadyCallback {

    lateinit var googleMap: GoogleMap
    lateinit var mark: Marker
    var canAddMarker: Boolean = true;

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.mapView) as SupportMapFragment
        mapFragment.getMapAsync(this)


        view.btCanAdd.setOnClickListener {

            canAddMarker = canAddMarker.not();

            if ( canAddMarker ){
                view.btCanAdd.setText("Add: ON")
                view.btCanAdd.setTextColor( Color.GREEN )
            }else{
                view.btCanAdd.setText("Add: OFF")
                view.btCanAdd.setTextColor( Color.RED )
            }

        }

    }

    override fun onMapReady(googleMap: GoogleMap) {

        this.googleMap = googleMap;

        googleMap.animateCamera( CameraUpdateFactory.newLatLngZoom ( craiova, 12.3F ) )

        googleMap.addMarker(MarkerOptions()
                .position(craiova)
                .title("Craiova Lat:" + craiova.latitude + " Lng:" + craiova.longitude ))

        googleMap.setOnMapClickListener { clickLatLng ->
            if ( canAddMarker ) {
                launchCamera()
                mark = googleMap.addMarker(MarkerOptions()
                        .position(clickLatLng)
                        .title(
                                "Lat: " + roundZecimals( clickLatLng.latitude, 4) +
                                " Lng:" + roundZecimals( clickLatLng.longitude, 4) ))
            }
        }
    }

    fun launchCamera(){
        if ( CameraPermissionsFragment.hasPermissions(requireContext()) ){
            val intent = Intent( MediaStore.ACTION_IMAGE_CAPTURE )
            startActivityForResult( intent, REQUEST_CapturePhotoFor_MapsMarker );
        }else{
            requestPermissions(PERMISSIONS_REQUIRED_CAMERA, PERMISSIONS_REQUEST_CAMERA_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if ( requestCode == REQUEST_CapturePhotoFor_MapsMarker ) {
            val image: Bitmap = data?.extras?.get("data") as Bitmap
            val icon = BitmapDescriptorFactory.fromBitmap(image);
            mark.setIcon( icon )
        }
    }

    companion object{

        val REQUEST_CapturePhotoFor_MapsMarker = 10

        val craiova = LatLng(44.31, 23.80)

        fun roundZecimals( number: Double, zecimals: Int ): Double{
            val factor = Math.pow( 10.0, zecimals.toDouble() );
            return Math.round(number * factor) / factor
        }
    }


}