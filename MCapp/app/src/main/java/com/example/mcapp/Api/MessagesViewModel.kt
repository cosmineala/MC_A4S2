package com.example.mcapp.Api

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch


class MessagesViewModel(application: Application) : AndroidViewModel(application) {

    val messagesHub = MessagesHub(application, this)

    val mesagesList = mutableListOf<Message>()


    var onMessagesListChanged: IExecutable


    var userPreferences: SharedPreferences

    init {
        onMessagesListChanged = object : IExecutable{
            override fun execute(list: List<Message>) {
                //Toast.makeText( application, "! IExecutable onMessagesListChanged runde without being defined", Toast.LENGTH_SHORT ).show()
            }

        }

        userPreferences = application.getSharedPreferences("UserInfo", 0)
    }

    fun getUsername(): String{
        return userPreferences.getString("Username", "").toString()

    }
    fun setUsername(name: String){
        val editor = userPreferences.edit()
        editor.putString("Username", name)
        editor.apply()
    }

    fun addMesage(message: Message){

        viewModelScope.launch(Dispatchers.IO) {
            mesagesList.add(message)

            viewModelScope.launch(Dispatchers.Main) {
                onMessagesListChanged.execute(mesagesList);

            }
        }


    }

    fun sendMessage(message: Message){
        viewModelScope.launch(Dispatchers.IO) {
            messagesHub.sendMessage(message)
        }
    }

    suspend fun OnMessagesListChanged() : Flow<List<Message>> = flow {

        var lastMessagesListSize = mesagesList.size

        while ( true ){
            if ( mesagesList.size != lastMessagesListSize ){
                emit(mesagesList)
                lastMessagesListSize = mesagesList.size
            }
            delay(DELAY_CHAT_UPDATE)
        }

    }

    companion object{

        val DELAY_CHAT_UPDATE = 300L

        interface IExecutable{
            fun execute(list: List<Message>)
        }
    }

}