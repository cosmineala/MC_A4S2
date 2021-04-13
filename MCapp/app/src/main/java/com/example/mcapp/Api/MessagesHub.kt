package com.example.mcapp.Api

import android.app.Application
import android.widget.Toast

import androidx.lifecycle.viewModelScope
import com.example.mcapp.R
import com.microsoft.signalr.HubConnection
import com.microsoft.signalr.HubConnectionBuilder
import com.microsoft.signalr.HubConnectionState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch


class MessagesHub( val application: Application, val messagesViewModel: MessagesViewModel) {

    val hubScope = messagesViewModel.viewModelScope

    val EMU = "http://10.0.2.2:51080/hub1"

    val DNS = application.resources.getString( R.string.MyDNS )
    val HttpPort = "51111"
    val URL = "http://" + DNS + ":" + HttpPort + "/hub1"

    lateinit var messagesHub: HubConnection


    init {
        startHub()
    }

    fun startHub( ){

        messagesHub = HubConnectionBuilder.create( URL ).build()

        configureHubMethods()

        messagesHub.onClosed {

            messagesHub.stop()
            SendToast("!!!Disconnected!!!");
            startHub()

        }

        connectAsync()
    }

    fun configureHubMethods(){

        messagesHub.on(
                "ReciveAll",
                { message ->

                    messagesViewModel.addMesage( message )

                }, Message::class.java
        )
    }

    fun connectAsync(){

        hubScope.launch(Dispatchers.IO) {

            messagesHub.start()
            delay( DELAY_CONNECT_ATEMPT_1 );

            if ( messagesHub.connectionState == HubConnectionState.CONNECTED)
            {
                SendToast("<<<Connected>>>");
            }
            else
            {
                delay( DELAY_CONNECT_ATEMPT_2 )
                if ( messagesHub.connectionState == HubConnectionState.CONNECTED)
                {
                    SendToast("<<<Connected>>>");
                }else
                {
                    startHub()
                }
            }
        }
    }

    fun sendMessage( message: Message ){

        if (IsConnected())
        {
            messagesHub.send("SendToAll", message)
        }
        else
        {
            hubScope.launch(Dispatchers.Main) {
                Toast.makeText(application, "Message can not be sent", Toast.LENGTH_SHORT).show()
            }
        }

    }

    fun SendToast( message: String ){
        hubScope.launch( Dispatchers.Main ) {
            Toast.makeText( application, message, Toast.LENGTH_SHORT ).show()
        }
    }

    fun IsConnected(): Boolean{
        return messagesHub.connectionState == HubConnectionState.CONNECTED
    }

    suspend fun onConnectionChanged() : Flow<Boolean> = flow {

        var lastConnectionState = HubConnectionState.DISCONNECTED

        while ( true )
        {
            if ( messagesHub.connectionState == HubConnectionState.CONNECTED && lastConnectionState == HubConnectionState.DISCONNECTED )
            {
                emit(true)
                lastConnectionState = HubConnectionState.CONNECTED
            }
            else if ( messagesHub.connectionState == HubConnectionState.DISCONNECTED && lastConnectionState == HubConnectionState.CONNECTED )
            {
                emit( false )
                lastConnectionState = HubConnectionState.DISCONNECTED
            }
            delay( 500 )
        }
    }

    companion object {

        val DELAY_CONNECT_ATEMPT_1 = 1000L
        val DELAY_CONNECT_ATEMPT_2 = 5000L

    }

}