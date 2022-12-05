package com.example.youtubeclone

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.squareup.picasso.Picasso

open class AdapterVideo(private val videos:ArrayList<Item>, val context:Context): Adapter<AdapterVideo.MyViewHolder>() {

    var onItemClick : ((Item) -> Unit)? = null
    lateinit var videoProp:Item
    var mainActivity: MainActivity = MainActivity()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterVideo.MyViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.adapter_video, parent,false)
        return MyViewHolder(view)
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
        holder.imageMenu.setOnClickListener{
            onItemClick?.invoke(videoProp)

            Log.d("Clique", "Objeto clicado adpter:" + videoProp.snippet.title)
        }



        holder.info.apply {
                text = videoProp.snippet.publishedAt.toString()
            }


    }

    class MyViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){

       var image = itemView.findViewById<ImageView>(R.id.imageView)
        var imageUser = itemView.findViewById<ImageView>(R.id.imageViewUser)
        var imageMenu = itemView.findViewById<ImageButton>(R.id.bottomSheet)
        val textTitle = itemView.findViewById<TextView>(R.id.textTitulo)
        val desc = itemView.findViewById<TextView>(R.id.textDesc)
        val info = itemView.findViewById<TextView>(R.id.textInfo)


    }


}


