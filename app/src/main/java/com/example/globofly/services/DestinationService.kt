package com.example.globofly.services

import com.example.globofly.models.Destination
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface DestinationService {

    @GET("destination")
    fun getDestinationList(@QueryMap mango:HashMap<String,String>):Call<List<Destination>>
    @GET("destination/{id}")
    fun getDestination(@Path("id") id:Int):Call<Destination>
}