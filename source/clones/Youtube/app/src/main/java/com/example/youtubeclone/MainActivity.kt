package com.example.youtubeclone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayer.OnInitializedListener
import com.google.android.youtube.player.YouTubePlayerView
import kotlin.contracts.Returns

open class MainActivity : YouTubeBaseActivity(){


    lateinit var playbackEventListerner:YouTubePlayer.PlaybackEventListener
    lateinit var playerStateChangeListener:YouTubePlayer.PlayerStateChangeListener
    val GOOGLE_API_KEY:String
        get() {
            return "AIzaSyCGY0rknZ1c6coLu28SpSGozl0AeiTO8XQ"
        }
    lateinit var youTubePlayerView: YouTubePlayerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializePlayer("YtHxKRFfqtg")
    }

    private fun initializePlayer(videoId:String){
        youTubePlayerView = findViewById(R.id.viewYouTubePlayerView)
        youTubePlayerView.initialize(GOOGLE_API_KEY, object : OnInitializedListener{
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
    }



}