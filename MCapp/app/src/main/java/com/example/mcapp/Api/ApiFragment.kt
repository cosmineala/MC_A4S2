package com.example.mcapp.Api

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.mcapp.R
import kotlinx.android.synthetic.main.fragment_api.view.*

class ApiFragment : Fragment() {

    private lateinit var myView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        myView = inflater.inflate(R.layout.fragment_api, container, false)

        return myView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        DEMO_API()
    }


    fun DEMO_API(){

        val exampleUrl = "http://jsonplaceholder.typicode.com/todos/1"
        val urlEEmulator = "http://10.0.2.2:51080/WeatherForecast"
        val urlLan = "http://192.168.5.65:51080/WeatherForecast"

        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, urlLan, null,
                { response ->
                    myView.tvApiTitle.text = "Response:\n" + response.toString()
                },
                { error ->
                    val er =  error.toString()
                    myView.tvApiTitle.text = error.toString() + " Cause:" + error.cause.toString() + " NR: " + error.networkResponse
                }
        )

        HttpSingleton.getInstance( requireContext() ).addToRequestQueue(jsonObjectRequest)
    }


}