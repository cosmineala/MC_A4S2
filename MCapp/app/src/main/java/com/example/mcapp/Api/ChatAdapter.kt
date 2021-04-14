package com.example.mcapp.Api

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.mcapp.R
import kotlinx.android.synthetic.main.item_recived_message.view.*
import java.math.BigInteger
import java.security.MessageDigest


class ChatAdapter(val chatFragment: ChatFragment, val recyclerView: RecyclerView): RecyclerView.Adapter<ChatAdapter.MyViewHolder>() {

    var mesagesList =  emptyList<Message>()

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){}


    override fun getItemViewType(position: Int): Int {

        if( isMyMessage( position ) )
        {
            return  VIEW_TYPE_SENTED
        }
        else
        {
            return  VIEW_TYPE_RECIVED
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        var viewHolder: MyViewHolder

        when( viewType )
        {
            VIEW_TYPE_SENTED -> viewHolder = buildView( parent, R.layout.item_sented_message )

            VIEW_TYPE_RECIVED -> viewHolder = buildView( parent, R.layout.item_recived_message )

            else -> viewHolder =  MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_sented_message, parent, false))
        }

        return viewHolder
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentItem = mesagesList[position]

        holder.itemView.apply {

            if( messageNeedsHead(position) )
            {
                tvMessageSender.isVisible = true
                tvMessageSender.setTextColor(generateColor(currentItem.sender))
                tvMessageSender.text = currentItem.sender + ":"
            }else
            {
                tvMessageSender.isVisible = false
            }

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


    fun messageNeedsHead(position: Int ): Boolean{
        if ( position == 0 )
        {
            return true
        }
        else return mesagesList[position].sender != mesagesList[position - 1].sender
    }
    fun isMyMessage( position: Int ): Boolean{
        return chatFragment.messagesViewModel.getUsername() == mesagesList[position].sender
    }
    fun buildView( parent: ViewGroup, viewId: Int ): MyViewHolder{
        return  MyViewHolder(LayoutInflater.from( parent.context ).inflate( viewId, parent, false))
    }




    companion object{

        val VIEW_TYPE_SENTED = 0
        val VIEW_TYPE_RECIVED = 1

        fun generateColor(name: String): Int{

            val hash = md5(name)
            val A = 0xFF

            var lot = hash.take(5)
            val R = lot.hashCode() % 256

            lot = hash.take(10).takeLast(5)
            val G = lot.hashCode() % 256

            lot = hash.take(15).takeLast(5)
            val B = lot.hashCode() % 256

            val color: Int = A and 0xff shl 24 or (R and 0xff shl 16) or (G and 0xff shl 8) or (B and 0xff)

            return color
        }


        fun md5(input: String): String {
            val md = MessageDigest.getInstance("MD5")
            return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
        }

    }

}