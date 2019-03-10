package com.gen.duo.app.service

import com.gen.duo.app.model.Base
import com.gen.duo.app.model.Item
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.*

/**
 * Created by israjhaliri on 3/19/18.
 */
interface ItemService {

    @GET("secret/get/item")
    fun getList(@Query("search") search: String, @Query("category") category: String, @Query("limit") limit: Int, @Query("offset") offset: Int): Call<List<Item>>

    @POST("secret/save/item")
    fun saveItem(@Body item: Item): Call<Any>
}