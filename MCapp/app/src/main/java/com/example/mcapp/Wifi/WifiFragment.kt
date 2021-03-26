package com.example.mcapp.Wifi

import android.content.Context.CONNECTIVITY_SERVICE
import android.content.Intent
import android.graphics.Color
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import com.example.mcapp.R
import kotlinx.android.synthetic.main.fragment_wifi.view.*
import kotlinx.coroutines.*


class WifiFragment : Fragment() {


    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_wifi, container, false)

        updateConnectionsUiStatus( view);


        view.btWifiOptions.setOnClickListener {
            val panelIntent = Intent(Settings.Panel.ACTION_WIFI)
            startActivityForResult(panelIntent, 1);
        }

        view.btDataOptions.setOnClickListener {
            val panelIntent = Intent(Settings.Panel.ACTION_INTERNET_CONNECTIVITY)
            startActivityForResult(panelIntent, 1);
        }

        // NOTE This is desabled from thr XML code
        view.btReftesh.setOnClickListener {
            updateConnectionsUiStatus(view)
        }

        lifecycleScope.launch {
            while (true) {
                //Toast.makeText(view.context, "Paralel", Toast.LENGTH_SHORT).show();
                updateConnectionsUiStatus( view );
                delay(500)
            }
        }
        

        return view
    }

   fun updateConnectionsUiStatus(view: View){
       val connectivityManager = view.context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
       val wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
       val mobile = connectivityManager .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

       if (wifi != null) {
           if (wifi.isConnected()){
               view.tvWifiState.setTextColor( Color.GREEN )
               view.tvWifiState.setText( " Wifi\nUSED" )
           }else{
               view.tvWifiState.setTextColor( Color.RED )
               view.tvWifiState.setText( "     Wifi\nNOT USED" )
           }
       }else{
           Toast.makeText(view.context, "No Wifi divicer", Toast.LENGTH_LONG ).show();
       }

       if (mobile != null) {
           if (mobile.isConnected()){
               view.tvDateState.setTextColor( Color.GREEN )
               view.tvDateState.setText( " Data\nUSED" )
           }else{
               view.tvDateState.setTextColor( Color.RED )
               view.tvDateState.setText( "     Data\nNOT USED" )
           }
       }else{
           Toast.makeText(view.context, "No Data divicer", Toast.LENGTH_LONG ).show();
       }


   }



}