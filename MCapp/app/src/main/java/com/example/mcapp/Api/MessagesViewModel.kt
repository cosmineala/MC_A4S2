package com.example.mcapp.Api

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.*
import com.example.mcapp.Cryptography.Crypto
import com.example.mcapp.Cryptography.Hash
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlin.random.Random


class MessagesViewModel(application: Application) : AndroidViewModel(application) {

    val messagesHub = MessagesHub(application, this)

    val mesagesList = mutableListOf<Message>()


    var onMessagesListChanged: IExecutable

    var userPreferences: SharedPreferences

    val crypto: Crypto = Crypto()


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


    fun TestAes( input: String ): String{

        val random = Random.nextInt(0,100)

        val key = "tp6EbnrwJGwrcjV3KpyqdmCysA2nSg=="
        val randomKey = crypto.aesNewKey()

        val randomSeed =  Hash.md5String(  random.toString() )
        val seed = "20"

        val encText = crypto.aesEncrypt( input, key, randomSeed )
        val decText = crypto.aesDecrypt( encText, key, randomSeed )

        val message = "Key: " + key + "\n\nEnc: " + encText + "\n\nDec: " + decText


        return message
    }

    fun TestRSA( input: String ): String{

        val keyPair = crypto.rsaNewKey()

        val encText = crypto.rsaEncryptWithPublic( input, keyPair.public )

        val decText = crypto.rsaDecryptWithPrivate( encText, keyPair.private )

        val message = "Private: " + keyPair.private.toString() +
                "\n\nPubluc: " + keyPair.public.toString() +
                "\n\nEnc: " + encText +
                "\n\nDec: " + decText

        print( message )

        return message
    }

}