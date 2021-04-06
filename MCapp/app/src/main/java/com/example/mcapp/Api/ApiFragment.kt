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


//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//    }

    private lateinit var myView: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        myView = inflater.inflate(R.layout.fragment_api, container, false)


        internrt()

        return myView
    }

    fun internrt(){

        val textView = myView.tvApiTitle


// Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(context )
        //var url = "https://www.google.com/"
       val url = "https://10.0.2.2:44364/WeatherForecast"
        val urlTest = "https://jsonplaceholder.typicode.com/todos/1"

// Request a string response from the provided URL.
        val josnRequest = JsonObjectRequest( Request.Method.GET, url, null,
            { response ->
                val resSum = response.toString()
                textView.text = resSum;
            },
            {
                textView.text = "That didn't work!"
            })

// Add the request to the RequestQueue.
        queue.add(josnRequest)
    }


}