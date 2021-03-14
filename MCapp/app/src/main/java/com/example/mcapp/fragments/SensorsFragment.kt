package com.example.mcapp.fragments

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.navigation.fragment.findNavController
import com.example.mcapp.R
import kotlinx.android.synthetic.main.fragment_sensors.*
import kotlinx.android.synthetic.main.fragment_sensors.view.*
import kotlin.math.roundToInt


class SensorsFragment : Fragment(), SensorEventListener {

//    private lateinit var sensorManager: SensorManager
//    private var sensor: Sensor? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_sensors, container, false)

        val sensorManager = view.context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY)

        sensorManager.registerListener( this, sensor, 10000 )

        view.tvinfo.setOnClickListener {
            findNavController().navigate( R.id.action_sensorsFragment_to_listFragment )
            //findNavController()
        }


        return view
    }

    override fun onSensorChanged(event: SensorEvent?) {

        if (event != null) {

            var v1: Float =  event.values[0]
            var v2: Float =  event.values[1]
            var v3: Float =  event.values[2]

            v1 = v1 * 10 / 2 + 50
            v2 = v2 * 10 / 2 + 50
            v3 = v3 * 10 / 2 + 50

            val pro1 : Int = v1.toInt()
            val pro2 : Int = v2.toInt()
            val pro3 : Int = v3.toInt()

            val textval = pro1.toString() + " : " + pro2 + " : " + pro3
            tvinfo.text = textval

            pb1.progress = pro1
            pb2.progress = pro2
            pb3.progress = pro3

        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }
}
