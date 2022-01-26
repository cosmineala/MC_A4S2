package com.example.mcapp.Api

import android.app.Application
import android.widget.Toast

import androidx.lifecycle.viewModelScope
import com.example.mcapp.Cryptography.Crypto
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

    val connectionBuilder = HubConnectionBuilder.create( URL )

    val crypto = Crypto()


    init {
        connectionBuilder.withHandshakeResponseTimeout( DELAY_CONNECT_CANCEL )
        startHub()
    }

    fun startHub( ){

        messagesHub = connectionBuilder.build()

        configureHubMethods()
        configureOnCLose()
        connectAsync()
    }

    fun configureHubMethods(){

        messagesHub.on(
                "ReciveAll",
                { encryptedMessage ->

                    val message = crypto.aesDecrypt( encryptedMessage, AES_KEY )

                    messagesViewModel.addMesage( message )

                }, Message::class.java
        )
    }

    fun configureOnCLose(){
        messagesHub.onClosed {

            messagesHub.stop()
            SendToast("!!!Disconnected!!!");
            startHub()

        }
    }

    fun connectAsync(){

        hubScope.launch(Dispatchers.IO) {

                messagesHub.start() 
                delay(DELAY_CONNECT_ATEMPT_1)

            while ( messagesHub.connectionState == HubConnectionState.DISCONNECTED )
                {
                    messagesHub.start()
                    delay(DELAY_CONNECT_ATEMPT_1)
                }
                SendToast("<<<Connected>>>");
                //startHub()


        }
    }

    fun sendMessage( message: Message ){

        val encrypteedMessage = crypto.aesEncrypt( message, AES_KEY )

        if (IsConnected())
        {
            messagesHub.send("SendToAll", encrypteedMessage)
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

        val AES_KEY = "tp6EbnrwJGwrcjV3KpyqdmCysA2nSg=="

        val DELAY_CONNECT_ATEMPT_1 = 3000L
        val DELAY_CONNECT_ATEMPT_2 = 5000L
        val DELAY_CONNECT_CANCEL = 500L

    }

}