package com.gen.duo.app.interactor

import android.content.Context
import com.gen.duo.app.model.Base
import com.gen.duo.app.service.LoginService
import com.gen.duo.app.util.RetrofitClient
import retrofit2.Call


/**
 * Created by israjhaliri on 3/8/18.
 */
class LoginInteractor {

    private var service: LoginService? = null

    fun init(context: Context) {
        val retrofit = RetrofitClient.getClientInterceptor(context)

        service = retrofit?.create<LoginService>(LoginService::class.java)
    }

    fun doLogin(username: String, password: String): Call<Base<String>> {
        return service!!.doLogin(username, password)
    }
}