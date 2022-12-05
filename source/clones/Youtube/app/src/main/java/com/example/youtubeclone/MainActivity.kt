package com.example.youtubeclone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ListAdapter
import android.widget.Toast
import android.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayer.OnInitializedListener
import com.google.android.youtube.player.YouTubePlayerView
import retrofit2.*
import kotlin.contracts.Returns

open class MainActivity : YouTubeBaseActivity(){


    lateinit var retrofit: Retrofit
    lateinit var youtubeService:YoutubeService

   // lateinit var videosLst: ArrayList<Video>
    lateinit var adapterVideo: AdapterVideo
    lateinit var recyclerView:RecyclerView
    lateinit var toolbar: Toolbar
    lateinit var playbackEventListerner:YouTubePlayer.PlaybackEventListener
    lateinit var playerStateChangeListener:YouTubePlayer.PlayerStateChangeListener

    lateinit var youTubePlayerView: YouTubePlayerView
    val lista = ArrayList<Item>()
    lateinit var bottomNav : BottomNavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Configurar toolbar
        toolbar = findViewById(R.id.toolbar)
        setActionBar(toolbar)
        confiInicial()
        bottomNav = findViewById(R.id.bottom_navigation) as BottomNavigationView
        //RecycleView
        recyclerView = findViewById(R.id.recycleVideo)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapterVideo = AdapterVideo(lista, applicationContext)

        recuperarVideos()


        adapterVideo.onItemClick.run {


            val dialog = BottomSheetDialog(this@MainActivity)
            val view = LayoutInflater.from(this@MainActivity).inflate(R.layout.bottom_sheet_dialog, null,false)
            dialog.setCancelable(true)
            dialog.setCanceledOnTouchOutside(true)
            dialog.setContentView(view)
                dialog.show()

                 Log.d("Clique", "Objeto clicado main:")
                Toast.makeText(this@MainActivity, "Item clicked ", Toast.LENGTH_SHORT).show()



        }



        toolbar.setOnClickListener{
            BottomSheet()
        }
       // initializePlayer("YtHxKRFfqtg")

    }

    open fun BottomSheet(){
        val dialog = BottomSheetDialog(this)
        val view = LayoutInflater.from(this)
            .inflate(R.layout.bottom_sheet_dialog, null)
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        dialog.setContentView(view)
        dialog.show()
    }


    /*  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
          menuInflater.inflate(R.menu.menu, menu)
          return  true //super.onCreateOptionsMenu(menu)
      }

      override fun onOptionsItemSelected(item: MenuItem): Boolean {
          return when (item.itemId){

              R.id.menu_media -> {
                  Toast.makeText(this, "media", Toast.LENGTH_SHORT).show()
                  true
              }
              R.id.menu_notificacao -> {
                  Toast.makeText(this, "notificação", Toast.LENGTH_SHORT).show()
                  true
              }
              R.id.menu_pesquisar -> {
                  Toast.makeText(this, "pesquisar", Toast.LENGTH_SHORT).show()
                  true
              }
              R.id.menu_user -> {
                  Toast.makeText(this, "user", Toast.LENGTH_SHORT).show()
                  true
              }
              else -> {
                  return super.onOptionsItemSelected(item)
              }
          }

      }
      */
    fun confiInicial(){
        retrofit = RetrofitConfig.getRetrofit()
    }
    fun recuperarVideos(){

        var service =  retrofit.create<YoutubeService>()
        service.recuperarVideos(
            "snippet",
            "date",
            "20",
             YoutubeConfig.GOOGLE_API_KEY,
             YoutubeConfig.CANAL_ID
        ).enqueue(object: Callback<Resultado>{
            override fun onResponse(call: Call<Resultado>, response: Response<Resultado>) {
                if(response.isSuccessful){
                    for (i in response.body()!!.items){
                        lista.add(i)

                    }
                    adapterVideo = AdapterVideo(lista, applicationContext)
                    recyclerView.adapter = adapterVideo

                   // Log.d("resultado","resultado: " + lista[0].snippet.title)
                }

            }

            override fun onFailure(call: Call<Resultado>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
    fun initializePlayer(videoId:String){
       /* youTubePlayerView = findViewById(R.id.viewYouTubePlayerView)
        youTubePlayerView.initialize(R.string.GOOGLE_API_KEY, object : OnInitializedListener{
            override fun onInitializationSuccess(
                p0: YouTubePlayer.Provider?,
                p1: YouTubePlayer?,
                p2: Boolean //Foi restaurado?
            ) {
               // p1!!.setPlaybackEventListener(playbackEventListerner)
                p1!!.setPlayerStateChangeListener(playerStateChangeListener)
                if(!p2){
                    p1!!.cueVideo(videoId) // Recupera posição do player
                   // p1.loadPlaylist(videoId)
                    p1.play()
                }


            }

            override fun onInitializationFailure(
                p0: YouTubePlayer.Provider?,
                p1: YouTubeInitializationResult?
            ) {
                Toast.makeText(applicationContext,
                    "Player falhou!",
                    Toast.LENGTH_SHORT)
                    .show()
            }

        });

        playerStateChangeListener=object :YouTubePlayer.PlayerStateChangeListener{
            override fun onLoading() {
                Toast.makeText(this@MainActivity,
                    "Carregando...",
                    Toast.LENGTH_LONG)
                    .show()
            }

            override fun onLoaded(p0: String?) {
                Toast.makeText(this@MainActivity,
                    "Carregado...",
                    Toast.LENGTH_LONG)
                    .show()
            }

            override fun onAdStarted() {
                Toast.makeText(this@MainActivity,
                    "Iniciado propaganda...",
                    Toast.LENGTH_LONG)
                    .show()
            }

            override fun onVideoStarted() {
                Toast.makeText(this@MainActivity,
                    "Video está começando...",
                    Toast.LENGTH_LONG)
                    .show()
            }

            override fun onVideoEnded() {
                Toast.makeText(this@MainActivity,
                    "Video chegou ao final...",
                    Toast.LENGTH_LONG)
                    .show()
            }

            override fun onError(p0: YouTubePlayer.ErrorReason?) {
                Toast.makeText(this@MainActivity,
                    "Erro ao recuperar eventos de carregamento.",
                    Toast.LENGTH_LONG)
                    .show()
            }

        }
        playbackEventListerner = object :YouTubePlayer.PlaybackEventListener{
            override fun onPlaying() {
                Toast.makeText(this@MainActivity,
                    "Iniciado...",
                    Toast.LENGTH_LONG)
                    .show()
            }

            override fun onPaused() {
                Toast.makeText(this@MainActivity,
                    "Pause...",
                    Toast.LENGTH_LONG)
                    .show()
            }

            override fun onStopped() {
                Toast.makeText(this@MainActivity,
                    "Parado...",
                    Toast.LENGTH_LONG)
                    .show()
            }

            override fun onBuffering(p0: Boolean) {
                Toast.makeText(this@MainActivity,
                    "onBuffering...",
                    Toast.LENGTH_LONG)
                    .show()
            }

            override fun onSeekTo(p0: Int) {
                Toast.makeText(this@MainActivity,
                    "onSeekTo...",
                    Toast.LENGTH_LONG)
                    .show()
            }

        }

        */
    }



}