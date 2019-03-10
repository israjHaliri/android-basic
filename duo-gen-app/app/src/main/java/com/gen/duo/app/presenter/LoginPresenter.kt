package com.gen.duo.app.presenter

import com.gen.duo.app.interactor.LoginInteractor
import com.gen.duo.app.model.Base
import com.gen.duo.app.util.Common
import com.gen.duo.app.view.LoginView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by israjhaliri on 3/8/18.
 */
class LoginPresenter() {

    lateinit var interactor: LoginInteractor
    lateinit var view: LoginView

    fun init(loginView: LoginView, loginInteractor: LoginInteractor){
        this.view = loginView
        this.interactor = loginInteractor
    }

    fun doLogin(username: String, password: String) {
        interactor.doLogin(username, password).enqueue(object : Callback<Base<String>> {
            override fun onResponse(call: Call<Base<String>>?, response: Response<Base<String>>?) {
                view.onAuthorized(response?.body()!!.copy())
            }

            override fun onFailure(call: Call<Base<String>>?, t: Throwable?) {
                t?.printStackTrace()
            }
        });
    }
}