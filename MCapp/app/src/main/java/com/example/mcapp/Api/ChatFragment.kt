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
import com.microsoft.signalr.HubConnection
import com.microsoft.signalr.HubConnectionBuilder
import com.microsoft.signalr.HubConnectionState
import kotlinx.android.synthetic.main.fragment_chat.view.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class ChatFragment : Fragment() {

    lateinit var recyclerView: RecyclerView

    val mesagesList =  mutableListOf<Message_Model>()

    lateinit var messagesViewModel: MessagesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_chat, container, false)


        adapter = ChatAdapter( this,  )
        recyclerView = view.rvChat
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        messagesViewModel = ViewModelProvider( requireActivity() ).get( MessagesViewModel::class.java )


        view.btSubmitMessage.setOnClickListener {

            val message = view.etEditText.text.toString()

            messagesViewModel.sendMessage( Message_Model( messagesViewModel.myName, message ) )

            view.etEditText.text.clear()

            //val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            //imm.hideSoftInputFromWindow(view.windowToken, 0)

        }

//        messagesViewModel.liveChange.observe( viewLifecycleOwner, Observer {
//            adapter.SetData( messagesViewModel.mesagesList )
//            recyclerView.scrollToPosition( messagesViewModel.mesagesList.size - 1 )
//        } )

        lifecycleScope.launch {

            var lastSize = messagesViewModel.mesagesList.size

            while(true){

                if (  messagesViewModel.mesagesList.size != lastSize ) {

                    adapter.SetData(messagesViewModel.mesagesList);



                    recyclerView.scrollToPosition(messagesViewModel.mesagesList.size - 1)
                    lastSize =  messagesViewModel.mesagesList.size
                }

                delay(300);
            }

        }


        return view
    }

    companion object {

        lateinit var adapter: ChatAdapter

    }
}