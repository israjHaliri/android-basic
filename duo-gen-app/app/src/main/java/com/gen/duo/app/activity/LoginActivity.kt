package com.gen.duo.app.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.view.View
import android.widget.*
import com.gen.duo.app.R
import com.gen.duo.app.interactor.LoginInteractor
import com.gen.duo.app.model.Base
import com.gen.duo.app.presenter.LoginPresenter
import com.gen.duo.app.util.Common
import com.gen.duo.app.util.Preferences
import com.gen.duo.app.view.LoginView


class LoginActivity : AppCompatActivity(), LoginView {

    lateinit var preferences: Preferences

    lateinit var  intercator: LoginInteractor
    lateinit var  presenter: LoginPresenter

    lateinit var progressBar: ProgressBar
    lateinit var loginLayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        intercator = LoginInteractor()
        intercator.init(this@LoginActivity)

        presenter = LoginPresenter()
        presenter.init(this, intercator)

        preferences = Preferences(this@LoginActivity)

        progressBar = findViewById<ProgressBar>(R.id.pgbar_common)
        loginLayout = findViewById<LinearLayout>(R.id.layout_login)

        progressBar.setVisibility(View.GONE);

        val btnSubmit = findViewById<Button>(R.id.button_submit)
        btnSubmit.setOnClickListener {
            val editTextUsername = findViewById<EditText>(R.id.edit_text_username)
            val editTextPassword = findViewById<EditText>(R.id.edit_text_password)

            var username = editTextUsername.text.toString();
            var password = editTextPassword.text.toString();

            doLogin(username, password)
        }

        var tvSignUp = findViewById<TextView>(R.id.text_view_sign_up)
        tvSignUp.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@LoginActivity, RegistrationActivity::class.java)
            finish()
            startActivity(intent)
        })
    }

    override fun doLogin(username: String, password: String) {
        loginLayout.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        presenter.doLogin(username, password)
    }

    override fun onAuthorized(base: Base<String>) {
        loginLayout.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);

        preferences.setToken(base.data)

        var status = base.status

        Common.showMessage(this@LoginActivity, status)

        when (status) {
            "success" -> {
                Common.showMessage(this@LoginActivity, status)

                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                finish()
                startActivity(intent)

            }
        }
    }
}
