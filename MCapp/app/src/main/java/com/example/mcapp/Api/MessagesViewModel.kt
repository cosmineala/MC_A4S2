package com.example.mcapp.Api

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.*
import com.example.mcapp.R
import com.microsoft.signalr.HubConnection
import com.microsoft.signalr.HubConnectionBuilder
import com.microsoft.signalr.HubConnectionState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MessagesViewModel( application: Application) : AndroidViewModel(application) {

    val EMU = "http://10.0.2.2:51080/hub1"

    var mesagesList: MutableList<Message>

    val DNS = getApplication<Application>().resources.getString( R.string.MyDNS )
    val HttpPort = "51111"
    val URL = "http://" + DNS + ":" + HttpPort + "/hub1"

    val messagesHub: HubConnection = HubConnectionBuilder.create( URL ).build()

    var myName = ""

    init{

        mesagesList = mutableListOf<Message>()

        configureMessagesHub( messagesHub )
        messagesHub.start()

        viewModelScope.launch{

            while( messagesHub.connectionState != HubConnectionState.CONNECTED ){

                Toast.makeText( application, "!!! Not conecteeed", Toast.LENGTH_SHORT ).show()
                //messagesHub.start()
                delay(5000);
            }

            Toast.makeText( application, "Conecteeed !!!", Toast.LENGTH_SHORT ).show()

        }
    }



    fun addMesage( message: Message ){

        mesagesList.add( message )

    }

    fun sendMessage( message: Message ){

        viewModelScope.launch(Dispatchers.IO){

            var max = 100L

            while ( messagesHub.connectionState != HubConnectionState.CONNECTED && max -- > 0 )
            {
                messagesHub.start()
                delay( max )
            }

            messagesHub.send("SendToAll", message )

        }


    }

    fun configureMessagesHub( hubConnection: HubConnection ){

        hubConnection.on(
                "ReciveAll",
                { message ->

                addMesage( message )

                }, Message::class.java
        )

    }



}