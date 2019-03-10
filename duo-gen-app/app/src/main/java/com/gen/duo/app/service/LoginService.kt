package com.gen.duo.app.service

import com.gen.duo.app.model.Base
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Query


/**
 * Created by israjhaliri on 3/19/18.
 */
interface LoginService {

    @POST("login")
    @FormUrlEncoded
    fun doLogin(@Field("username") username: String, @Field("password") password: String): Call<Base<String>>
}