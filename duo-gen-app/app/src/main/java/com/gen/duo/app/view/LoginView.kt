package com.gen.duo.app.view

import com.gen.duo.app.model.Base
import com.gen.duo.app.model.Item

/**
 * Created by israjhaliri on 3/8/18.
 */
interface LoginView {

    fun doLogin(username: String, password: String)

    fun onAuthorized(base: Base<String>)
}
