package com.example.globofly.services

import com.example.globofly.models.Destination
import retrofit2.Call
import retrofit2.http.*

interface DestinationService {

    @GET("destination")
    fun getDestinationList(@QueryMap mango:HashMap<String,String>):Call<List<Destination>>
    @GET("destination/{id}")
    fun getDestination(@Path("id") id:Int):Call<Destination>
    @GET("messages")
    fun getMessages():Call<String>
    @POST("destination")
    fun addDestination(@Body destination: Destination):Call<Destination>
//    @PUT("destination/{id}")
//    fun updateDestination(@Path("id") id:Int,@Body destination: Destination):Call<Destination>
    @FormUrlEncoded
@PUT("destination/{id}")
fun updateDestination(@Path("id") id:Int,
                      @Field("city") city: String?,
                      @Field("description") description:String?,
                      @Field("country") country:String?):Call<Destination>

}