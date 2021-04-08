package com.example.mcapp.Api

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.text.set
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.mcapp.R
import com.microsoft.signalr.HubConnection
import com.microsoft.signalr.HubConnectionState
import kotlinx.android.synthetic.main.fragment_api.view.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class ApiFragment : Fragment() {

    private lateinit var myView: View

    lateinit var messagesViewModel: MessagesViewModel


   //val sharedPref: SharedPreferences = requireActivity().getSharedPreferences("HUB_SETINGS", Context.MODE_PRIVATE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        myView = inflater.inflate(R.layout.fragment_api, container, false)

        return myView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        messagesViewModel = ViewModelProvider( requireActivity() ).get( com.example.mcapp.Api.MessagesViewModel::class.java )

        myView.btEnterChat.setOnClickListener {

            messagesViewModel.myName= myView.etEnterName.text.toString()
            //sharedPref.edit().putString( "MY_NAME", MY_NAME ).apply()


            val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)

            findNavController().navigate(R.id.action_apiFragment_to_chatFragment)

        }


    }

    override fun onResume() {
        super.onResume()

        myView.etEnterName.setText( messagesViewModel.myName )
    }



    companion object{

        val SERVER_URL_EMULATOR = "http://10.0.2.2:51080/hub1"
        val SERVER_URL_LAN = "http://192.168.5.65:51080/hub1"
        val SERVER_URL_WAN = "http://cnegri.go.ro:51080/hub1"


    }


}
