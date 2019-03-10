package com.gen.duo.app.view

import com.gen.duo.app.model.Item

/**
 * Created by israjhaliri on 3/8/18.
 */
interface MainView {

    fun getHotList(listItem: List<Item>)

    fun getRecomendList(listItem: List<Item>)

    fun onUnAuthorized()
}