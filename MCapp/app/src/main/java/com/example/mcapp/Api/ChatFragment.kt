package com.example.mcapp.Api

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mcapp.R
import kotlinx.android.synthetic.main.fragment_chat.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*


class ChatFragment : Fragment() {

    lateinit var chatRecyclerView: RecyclerView

    lateinit var chatAdapter: ChatAdapter
    lateinit var messagesViewModel: MessagesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onResume() {
        super.onResume()

        messagesViewModel.sendMessage(Message(id = UUID.randomUUID(), sender = messagesViewModel.getUsername(), content = "Joined Chat"))
        //chatAdapter.SetData(messagesViewModel.mesagesList);


    }

    override fun onPause() {
        super.onPause()

        messagesViewModel.sendMessage(Message(id = UUID.randomUUID(), sender = messagesViewModel.getUsername(), content = "Left Chat"))
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_chat, container, false)

        chatRecyclerView = view.rvChat

        chatAdapter = ChatAdapter(this, chatRecyclerView)

        chatRecyclerView.adapter = chatAdapter
        chatRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        messagesViewModel = ViewModelProvider(requireActivity()).get(MessagesViewModel::class.java)


        view.btSubmitMessage.setOnClickListener {

            val message = view.etEditText.text.toString()
            messagesViewModel.sendMessage(Message(id = UUID.randomUUID(), sender = messagesViewModel.getUsername(), content = message))
            view.etEditText.text.clear()

        }

        view.etEditText.setOnClickListener {
            lifecycleScope.launch(Dispatchers.Main){
                delay(500)
                chatAdapter.smoothScrollDown()
            }

        }

        messagesViewModel.onMessagesListChanged =  object : MessagesViewModel.Companion.IExecutable{
            override fun execute(list: List<Message>) {
                chatAdapter.SetData(list);
                chatAdapter.smoothScrollDown()
            }

        }


        return view
    }


}