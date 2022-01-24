package com.example.bottnav

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

//로그인 화면
class LoginActivity : AppCompatActivity() {
    lateinit var btnLogin: Button
    lateinit var regText : TextView
    lateinit var editEmail : EditText
    lateinit var editPassword : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnLogin = findViewById<Button>(R.id.btnLogin)
        regText = findViewById(R.id.regText)
        editEmail = findViewById(R.id.editEmail)
        editPassword = findViewById(R.id.editPassword)

        //버튼 누르면 메인 화면 진입
        btnLogin.setOnClickListener {
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        }

        //회원가입 누르면 회원가입 창으로 이동
        regText.setOnClickListener{
            var intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

    }
}