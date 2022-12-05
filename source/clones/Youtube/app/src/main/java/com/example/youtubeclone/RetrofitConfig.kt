package com.example.youtubeclone

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

open class RetrofitConfig {


    companion object {
        open fun getRetrofit(): Retrofit {

            return Retrofit.Builder()
                .baseUrl(YoutubeConfig.URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        }
    }

}