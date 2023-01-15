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

    lateinit var videoProp:Item
    private lateinit var mListener:onItemClickListener
    private lateinit var mListener2:onItemClickListenerPosition
    interface onItemClickListener{
        fun onItemClick(idVideo:String, title:String, description:String, public:String )
    }
    interface onItemClickListenerPosition{
        fun onItemClickPosition(position:Int)
    }
    fun setOnItemClickListerner(listener:onItemClickListener){
        mListener=listener

    }
    fun setOnItemClickListernerPosition(listener2:onItemClickListenerPosition){
        mListener2=listener2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterVideo.MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_video, parent,false)

        return MyViewHolder(view, mListener, mListener2)
    }

    override fun getItemCount(): Int {
        return videos.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        videoProp = videos[position]
        holder.videoProp = videos[position]
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

    class MyViewHolder(itemView: View, mlistener: onItemClickListener, mlistener2: onItemClickListenerPosition) :RecyclerView.ViewHolder(itemView){

        lateinit var videoProp:Item
       var image = itemView.findViewById<ImageView>(R.id.imageView)
        var imageUser = itemView.findViewById<ImageView>(R.id.imageViewUser)
        var imageMenu = itemView.findViewById<ImageButton>(R.id.bottomSheet)
        val textTitle = itemView.findViewById<TextView>(R.id.textTitulo)
        val desc = itemView.findViewById<TextView>(R.id.textDesc)
        val info = itemView.findViewById<TextView>(R.id.textInfo)

        init {

            image.setOnClickListener {
                mlistener.onItemClick(videoProp.id.videoId, videoProp.snippet.title, videoProp.snippet.description, videoProp.snippet.publishedAt.toString() )
            }
            imageMenu.setOnClickListener {
                mlistener2.onItemClickPosition(adapterPosition)
            }

        }

    }


}


