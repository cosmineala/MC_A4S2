package com.example.mcapp

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.content.Intent
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.fragment_wifi.*
import kotlinx.android.synthetic.main.fragment_wifi.view.*


class WifiFragment : Fragment() {

    private lateinit var wifiManager: WifiManager

    //private lateinit var view: View


//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        //WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
//
//
//    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_wifi, container, false)

        wifiManager = view.context.getSystemService(Context.WIFI_SERVICE) as WifiManager


        updateWifiView( view, view.context );


        view.btWifiOptions.setOnClickListener {
            val panelIntent = Intent(Settings.Panel.ACTION_WIFI)
            startActivityForResult(panelIntent, 1);
        }

        view.btReftesh.setOnClickListener {
            updateWifiView(view, view.context)
        }


        return view
    }

   fun updateWifiView( view: View, context: Context ){
       val connManager = view.context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
       val wifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
       val mobile = connManager .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

       if (wifi != null) {
           if (wifi.isConnected()){
               view.tvWifiState.setTextColor( Color.GREEN )
               view.tvWifiState.setText( "Wifi ON" )
           }else{
               view.tvWifiState.setTextColor( Color.RED )
               view.tvWifiState.setText( "Wifi OFF" )
           }
       }else{
           Toast.makeText(view.context, "Noooo", Toast.LENGTH_LONG ).show();
       }

//       if (mobile != null) {
//           if (mobile.isConnected()) {
//               // If Internet connected
//           }
//       }
   }



}