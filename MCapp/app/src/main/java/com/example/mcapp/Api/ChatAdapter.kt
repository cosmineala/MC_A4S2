package com.example.mcapp.Api

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mcapp.R
import kotlinx.android.synthetic.main.item_message.view.*

class ChatAdapter( val chatFragment: ChatFragment ): RecyclerView.Adapter<ChatAdapter.MyViewHolder>() {

    var mesagesList =  emptyList<Message_Model>()

    class MyViewHolder( itemView: View): RecyclerView.ViewHolder(itemView){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val viewHolder = MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_message, parent, false))
        return viewHolder
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentItem = mesagesList[position]

        holder.itemView.apply {
            tvMessageSender.text = currentItem.sender
            tvMessageContent.text = currentItem.content
        }
    }

    override fun getItemCount(): Int {
        return mesagesList.count()
    }


    fun SetData( list: List<Message_Model> ){
        this.mesagesList = list
        notifyDataSetChanged()
    }
}