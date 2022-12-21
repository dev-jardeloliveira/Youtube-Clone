package com.example.youtubeclone

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.youtube.player.YouTubeBaseActivity
import retrofit2.*

open class MainActivity : YouTubeBaseActivity(){


    lateinit var retrofit: Retrofit
    lateinit var adapterVideo: AdapterVideo
    lateinit var recyclerView:RecyclerView
    lateinit var toolbar: Toolbar

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

        recuperarVideos()

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
    fun  recuperarVideos(){

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
                    adapterVideo = AdapterVideo(lista)
                    recyclerView.adapter = adapterVideo

                    adapterVideo.setOnItemClickListerner(object : AdapterVideo.onItemClickListener{
                        override fun onItemClick(position: Int) {
                            BottomSheet()
                        }

                    })


                }

            }

            override fun onFailure(call: Call<Resultado>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }




}


