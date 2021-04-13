package com.example.mcapp.Api

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.lang.Exception

class MessagesViewModel( application: Application) : AndroidViewModel(application) {

    val messagesHub = MessagesHub( application, this )

    val mesagesList = mutableListOf<Message>()

    var myName = ""

    fun addMesage( message: Message ){

        try {

            viewModelScope.launch( Dispatchers.IO ) {
                mesagesList.add(message)
            }
        }catch ( e: Exception){

        }
    }

    fun sendMessage( message: Message ){
        viewModelScope.launch( Dispatchers.IO ) {
            messagesHub.sendMessage( message )
        }
    }

    suspend fun OnMessagesListChanged() : Flow<List<Message>> = flow {

        var lastMessagesListSize = mesagesList.size

        while ( true ){
            if ( mesagesList.size != lastMessagesListSize ){
                emit( mesagesList )
                lastMessagesListSize = mesagesList.size
            }
            delay( DELAY_CHAT_UPDATE)
        }

    }

    companion object{

        val DELAY_CHAT_UPDATE = 300L
    }

}