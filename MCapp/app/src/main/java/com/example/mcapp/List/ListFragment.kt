package com.example.mcapp.List

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mcapp.Api.MessagesViewModel
import com.example.mcapp.R
import kotlinx.android.synthetic.main.fragment_list.view.*


class ListFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_list, container, false)

        view.sensor_icon.setOnClickListener {
//            findNavController().navigate( R.id.action_listFragment_to_sensorsFragment2 )
            findNavController().navigate(R.id.action_listFragment_to_sensorsFragment)
        }

        view.camera_icon.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_cameraPermissionsFragment)
        }

        view.wifi_icon.setOnClickListener {
           findNavController().navigate(R.id.action_listFragment_to_wifiFragment)
        }

        view.maps_icon.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_mapsFragment)
        }

        view.api_icon.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_apiFragment)
        }


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val messagesViewModel = ViewModelProvider( requireActivity() ).get( MessagesViewModel::class.java )
    }



}