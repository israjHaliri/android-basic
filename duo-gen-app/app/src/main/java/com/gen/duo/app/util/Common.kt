package com.gen.duo.app.util

import android.content.Context
import android.view.Gravity
import android.widget.Toast
import java.util.logging.Logger

/**
 * Created by israjhaliri on 3/10/18.
 */
class Common{

    companion object {
        fun showMessage(context: Context, message: String) {
            val toast = Toast.makeText(context, message, Toast.LENGTH_LONG)
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
        }

        fun getLogger(o: Any): Logger{
            val LOGGER = Logger.getLogger(o::class.java.name)
            return LOGGER;
        }
    }
}