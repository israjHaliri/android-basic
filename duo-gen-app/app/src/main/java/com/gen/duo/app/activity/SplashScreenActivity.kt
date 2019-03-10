package com.gen.duo.app.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.gen.duo.app.R
import com.gen.duo.app.util.Common
import com.gen.duo.app.util.Preferences

class SplashScreenActivity : AppCompatActivity() {
    private val SPLASH_DELAY: Long = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash_screen)

        Handler().postDelayed(object : Runnable {
            public override fun run() {
                val preferences = Preferences(this@SplashScreenActivity)

                var token: String = preferences.getToken()

                Common.getLogger(this).info("Token : " + token);

                when {
                    token.isEmpty()-> {
                        startActivity(Intent(this@SplashScreenActivity, LoginActivity::class.java))
                        finish()
                    }
                    else -> {
                        startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
                        finish()
                    }
                }
            }
        }, SPLASH_DELAY.toLong())
    }
}
