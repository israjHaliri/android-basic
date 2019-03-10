package com.gen.duo.app.presenter

import android.content.Context
import com.gen.duo.app.interactor.LoginInteractor
import com.gen.duo.app.interactor.MainInteractor
import com.gen.duo.app.model.Base
import com.gen.duo.app.model.Item
import com.gen.duo.app.util.Common
import com.gen.duo.app.util.Preferences
import com.gen.duo.app.view.LoginView
import com.gen.duo.app.view.MainView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by israjhaliri on 3/8/18.
 */
class MainPresenter() {

    lateinit var interactor: MainInteractor
    lateinit var view: MainView

    fun init(mainView: MainView, mainInteractor: MainInteractor){
        this.view = mainView
        this.interactor = mainInteractor
    }

    fun getHotList() {
        interactor.getHotList().enqueue(object : Callback<List<Item>> {
            override fun onResponse(call: Call<List<Item>>?, response: Response<List<Item>>?) {
                if (response != null) {
                    if(response.code().equals(403) || response.code().equals("403")){
                        view.onUnAuthorized()
                    }else{
                        view.getHotList(response?.body()!!)
                    }
                }
            }

            override fun onFailure(call: Call<List<Item>>?, t: Throwable?) {
                t?.printStackTrace()
            }
        });
    }

    fun getRecomendList() {
        interactor.getRecomendList().enqueue(object : Callback<List<Item>> {
            override fun onResponse(call: Call<List<Item>>?, response: Response<List<Item>>?) {
                if (response != null) {
                    if(response.code().equals(403) || response.code().equals("403")){
                        view.onUnAuthorized()
                    }else{
                        view.getRecomendList(response?.body()!!)
                    }
                }
            }

            override fun onFailure(call: Call<List<Item>>?, t: Throwable?) {
                t?.printStackTrace()
            }
        });
    }
}