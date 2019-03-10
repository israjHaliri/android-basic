package com.gen.duo.app.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.gen.duo.app.R
import com.gen.duo.app.util.Common

class RegistrationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        val btnSubmit = findViewById<Button>(R.id.button_submit)

        btnSubmit.setOnClickListener {
            Common.showMessage(this@RegistrationActivity, "Ops sorry this feature under development")
        }
    }
}
