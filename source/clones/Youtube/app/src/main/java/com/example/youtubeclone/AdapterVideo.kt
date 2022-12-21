package com.example.youtubeclone

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.squareup.picasso.Picasso

open class AdapterVideo(private val videos:ArrayList<Item>): Adapter<AdapterVideo.MyViewHolder>() {

    private lateinit var mListener:onItemClickListener
    lateinit var videoProp:Item

    interface onItemClickListener{
        fun onItemClick(position:Int)
    }
    fun setOnItemClickListerner(listener:onItemClickListener){
        mListener=listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterVideo.MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_video, parent,false)

        return MyViewHolder(view, mListener)
    }

    override fun getItemCount(): Int {
        return videos.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        videoProp = videos[position]

       Picasso.get().load(videoProp.snippet.thumbnails.high.url)
            .resize(1600,1080)
            .centerInside()
            .into(holder.image)

        holder.textTitle.apply {
            text = videoProp.snippet.title
        }
        holder.desc.apply {
                text = videoProp.snippet.description
        }
        holder.imageUser.setImageResource(R.drawable.user)
        holder.imageMenu.setImageResource(R.drawable.menu)

        holder.info.apply {
                text = videoProp.snippet.publishedAt.toString()
            }


    }

    class MyViewHolder(itemView: View, listener: onItemClickListener) :RecyclerView.ViewHolder(itemView){

       var image = itemView.findViewById<ImageView>(R.id.imageView)
        var imageUser = itemView.findViewById<ImageView>(R.id.imageViewUser)
        var imageMenu = itemView.findViewById<ImageButton>(R.id.bottomSheet)
        val textTitle = itemView.findViewById<TextView>(R.id.textTitulo)
        val desc = itemView.findViewById<TextView>(R.id.textDesc)
        val info = itemView.findViewById<TextView>(R.id.textInfo)

        init {

            itemView.setOnClickListener {
               // listener.onItemClick(adapterPosition)
            }
            imageMenu.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }

        }

    }


}


