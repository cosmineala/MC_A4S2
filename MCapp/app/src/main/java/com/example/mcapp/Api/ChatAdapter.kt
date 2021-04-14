package com.example.mcapp.Api

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mcapp.R
import kotlinx.android.synthetic.main.item_message.view.*
import java.math.BigInteger
import java.security.MessageDigest
import kotlin.random.Random


class ChatAdapter(val chatFragment: ChatFragment, val recyclerView: RecyclerView): RecyclerView.Adapter<ChatAdapter.MyViewHolder>() {

    var mesagesList =  emptyList<Message>()

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val viewHolder = MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_message, parent, false))
        return viewHolder
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentItem = mesagesList[position]

        holder.itemView.apply {

//            if ( chatFragment.messagesViewModel.getUsername() == currentItem.sender )
//            {
//                tvMessageSender.setTextColor(getResources().getColor(R.color.purple_700))
//            }else{
//
//                val color = generateColor( currentItem.sender )
//
//                tvMessageSender.setTextColor(color)
//            }

            tvMessageSender.setTextColor( generateColor( currentItem.sender ) )

            tvMessageSender.text = currentItem.sender + ":"
            tvMessageContent.text = currentItem.content
        }
    }

    override fun getItemCount(): Int {
        return mesagesList.count()
    }


    fun SetData(newList: List<Message>){
        mesagesList = newList
        notifyDataSetChanged()
    }

    fun smoothScrollDown(){
        recyclerView.smoothScrollToPosition(mesagesList.size - 1)
    }

    companion object{

        fun generateColor( name: String ): Int{

            val hash = md5( name )
            val A = 0xFF

            var lot = hash.take(5)
            val R = lot.hashCode() % 256

            lot = hash.take(10).takeLast(5)
            val G = lot.hashCode() % 256

            lot = hash.take( 15).takeLast(5)
            val B = lot.hashCode() % 256

            val color: Int = A and 0xff shl 24 or (R and 0xff shl 16) or (G and 0xff shl 8) or (B and 0xff)

            return color
        }


        fun md5(input:String): String {
            val md = MessageDigest.getInstance("MD5")
            return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
        }

    }

}