package com.gen.duo.app.view

import com.gen.duo.app.model.Item

/**
 * Created by israjhaliri on 3/8/18.
 */
interface ItemView {

    fun getList(listItem: List<Item>)

    fun saveItem()

    fun onUnAuthorized()
}