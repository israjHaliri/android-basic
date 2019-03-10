package com.gen.duo.app.interactor

import android.content.Context
import android.content.SharedPreferences
import com.gen.duo.app.model.Base
import com.gen.duo.app.model.Item
import com.gen.duo.app.service.MainService
import com.gen.duo.app.util.Preferences
import com.gen.duo.app.util.RetrofitClient
import retrofit2.Call

/**
 * Created by israjhaliri on 3/8/18.
 */
class MainInteractor {

    private var service: MainService? = null

    fun init(context: Context) {
        val retrofit = RetrofitClient.getClientInterceptor(context)

        service = retrofit?.create<MainService>(MainService::class.java)
    }

    fun getHotList(): Call<List<Item>> {
        return service!!.getHotList()
    }

    fun getRecomendList(): Call<List<Item>> {
        return service!!.getRecomendList()
    }
}