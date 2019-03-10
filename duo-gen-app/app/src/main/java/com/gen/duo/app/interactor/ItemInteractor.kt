package com.gen.duo.app.interactor

import android.content.Context
import com.gen.duo.app.model.Base
import com.gen.duo.app.model.Item
import com.gen.duo.app.service.ItemService
import com.gen.duo.app.service.MainService
import com.gen.duo.app.util.RetrofitClient
import retrofit2.Call
import java.util.*

/**
 * Created by israjhaliri on 3/8/18.
 */
class ItemInteractor {

    private var service: ItemService? = null

    fun init(context: Context) {
        val retrofit = RetrofitClient.getClientInterceptor(context)

        service = retrofit?.create<ItemService>(ItemService::class.java)
    }

    fun getList(search: String, category: String, limit: Int, offset: Int): Call<List<Item>> {
        return service!!.getList(search, category, limit, offset)
    }

    fun saveItem(item: Item): Call<Any> {
        return service!!.saveItem(item)
    }
}