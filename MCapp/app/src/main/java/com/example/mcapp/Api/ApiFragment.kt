package com.example.mcapp.Api

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mcapp.R
import com.google.android.material.snackbar.Snackbar

import kotlinx.android.synthetic.main.fragment_api.view.*


class ApiFragment : Fragment() {

    private lateinit var myView: View

    lateinit var messagesViewModel: MessagesViewModel



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

        messagesViewModel = ViewModelProvider( requireActivity() ).get( MessagesViewModel::class.java )

        myView.etEnterName.setText( messagesViewModel.getUsername() )

        myView.btEnterChat.setOnClickListener {

            val name = myView.etEnterName.text.toString()

            if ( name != "" ) {
                messagesViewModel.setUsername( name )

                if (messagesViewModel.messagesHub.IsConnected()) {

                    val keyboard = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    keyboard.hideSoftInputFromWindow(view.windowToken, 0)

                    findNavController().navigate(R.id.action_apiFragment_to_chatFragment)
                }
                else
                {
                    Snackbar.make(
                        myView,
                        "Can not enter chat because you are not connected to the server",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
            else
            {
                Snackbar.make(
                    myView,
                    "You need a name to join the chat",
                    Snackbar.LENGTH_SHORT
                ).show()
            }



        }


    }

    override fun onResume() {
        super.onResume()

        myView.etEnterName.setText( messagesViewModel.getUsername() )
    }


}
