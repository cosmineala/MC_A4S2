package com.example.mcapp.Api

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Adapter
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mcapp.R
import com.google.android.gms.common.api.internal.LifecycleActivity
import com.microsoft.signalr.HubConnection
import com.microsoft.signalr.HubConnectionBuilder
import com.microsoft.signalr.HubConnectionState
import kotlinx.android.synthetic.main.fragment_chat.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*


class ChatFragment : Fragment() {

    lateinit var recyclerView: RecyclerView


    lateinit var messagesViewModel: MessagesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onResume() {
        super.onResume()

        adapter.SetData(messagesViewModel.mesagesList);

    }

    override fun onPause() {
        super.onPause()

        messagesViewModel.sendMessage( Message( id = UUID.randomUUID() , sender =  messagesViewModel.myName ,  content = "Left Chat" ) )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_chat, container, false)

        recyclerView = view.rvChat

        adapter = ChatAdapter( this, recyclerView )

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())


        messagesViewModel = ViewModelProvider( requireActivity() ).get( MessagesViewModel::class.java )
        messagesViewModel.sendMessage( Message( id = UUID.randomUUID() , sender =  messagesViewModel.myName ,  content = "Joined Chat" ) )

        view.btSubmitMessage.setOnClickListener {

            val message = view.etEditText.text.toString()
            messagesViewModel.sendMessage( Message( id = UUID.randomUUID() , sender =  messagesViewModel.myName ,  content = message ) )
            view.etEditText.text.clear()

        }


        lifecycleScope.launch{
            messagesViewModel.OnMessagesListChanged().collect {
                adapter.SetData( it );
                recyclerView.scrollToPosition(it.size - 1)
            }
        }


        return view
    }

    companion object {

        lateinit var adapter: ChatAdapter

    }
}