package com.gen.duo.app.service

import com.gen.duo.app.model.Base
import com.gen.duo.app.model.Item
import com.gen.duo.app.util.Preferences
import retrofit2.Call
import retrofit2.http.*

/**
 * Created by israjhaliri on 3/19/18.
 */
interface MainService {

    @GET("secret/get/item/hot")
    fun getHotList(): Call<List<Item>>

    @GET("secret/get/item/recomend")
    fun getRecomendList(): Call<List<Item>>
}