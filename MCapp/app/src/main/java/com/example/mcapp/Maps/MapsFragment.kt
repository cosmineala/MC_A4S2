package com.example.mcapp.Maps

import android.content.Intent
import android.graphics.Bitmap
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mcapp.MyCameraX.CameraPermissionsFragment
import com.example.mcapp.MyCameraX.PERMISSIONS_REQUEST_CODE
import com.example.mcapp.MyCameraX.PERMISSIONS_REQUIRED
import com.example.mcapp.R

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_maps.view.*

class MapsFragment : Fragment(), OnMapReadyCallback {

    lateinit var googleMap: GoogleMap

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.mapView) as SupportMapFragment
        mapFragment.getMapAsync(this)


        view.btMapAddIcon.setOnClickListener {

            if ( CameraPermissionsFragment.hasPermissions(requireContext()) ){
                val intent = Intent( MediaStore.ACTION_IMAGE_CAPTURE )
                startActivityForResult( intent, 100 );
            }else{
                requestPermissions(PERMISSIONS_REQUIRED, PERMISSIONS_REQUEST_CODE)
            }

        }



    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if ( requestCode == 100 ) {

            val image: Bitmap = data?.extras?.get("data") as Bitmap

            val bd = BitmapDescriptorFactory.fromBitmap(image);

            googleMap.addMarker(MarkerOptions().position(craiova).title("Marker in Craiova").icon(bd))

        }

    }

    override fun onMapReady(googleMap: GoogleMap) {

        this.googleMap = googleMap;

        googleMap.animateCamera( CameraUpdateFactory.newLatLngZoom ( craiova, 12.3F ) )

        googleMap.setOnMapClickListener { clickLatLng ->

            googleMap.addMarker(MarkerOptions().position( clickLatLng ).title("Clicked"))
        }
    }

    companion object{
        val craiova = LatLng(44.31, 23.80)
    }


}