package com.example.youtubeclone


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

open interface YoutubeService {

     @GET("search")
     open fun recuperarVideos (@Query("part") part:String,
                         @Query("order") order:String,
                         @Query("maxResults") maxResults:String,
                         @Query("key") key:String,
                         @Query("channelId") channelId:String): Call<Resultado>
}