package com.example.mcapp.List

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
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
            findNavController().navigate(R.id.action_listFragment_to_cameraFragment)
        }

        // Inflate the layout for this fragment
        return view
    }

    override fun onResume() {
        super.onResume()
    }


}