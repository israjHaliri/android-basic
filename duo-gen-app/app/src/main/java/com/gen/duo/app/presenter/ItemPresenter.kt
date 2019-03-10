package com.gen.duo.app.presenter

import com.gen.duo.app.interactor.ItemInteractor
import com.gen.duo.app.model.Base
import com.gen.duo.app.model.Item
import com.gen.duo.app.view.ItemView
import com.gen.duo.app.view.MainView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

/**
 * Created by israjhaliri on 3/8/18.
 */
class ItemPresenter() {

    lateinit var interactor: ItemInteractor
    lateinit var view: ItemView

    fun init(itemView: ItemView, itemInteractor: ItemInteractor){
        this.view = itemView
        this.interactor = itemInteractor
    }

    fun getList(search: String, category: String, limit: Int, offset: Int) {
        interactor.getList(search, category, limit, offset).enqueue(object : Callback<List<Item>> {
            override fun onResponse(call: Call<List<Item>>?, response: Response<List<Item>>?) {
                if (response != null) {
                    if (response.code().equals(403) || response.code().equals("403")) {
                        view.onUnAuthorized()
                    } else {
                        view.getList(response?.body()!!)
                    }
                }
            }

            override fun onFailure(call: Call<List<Item>>?, t: Throwable?) {
                t?.printStackTrace()
            }
        });
    }

    fun saveItem(item: Item) {
        interactor.saveItem(item).enqueue(object : Callback<Any> {
            override fun onResponse(call: Call<Any>?, response: Response<Any>?) {
                if (response != null) {
                    if (response.code().equals(403) || response.code().equals("403")) {
                        view.onUnAuthorized()
                    } else {
                        view.saveItem()
                    }
                }
            }

            override fun onFailure(call: Call<Any>?, t: Throwable?) {
                t?.printStackTrace()
            }
        });
    }
}