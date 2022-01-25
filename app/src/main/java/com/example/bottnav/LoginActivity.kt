package com.example.bottnav

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.annotation.RequiresApi
import java.time.LocalDate

//로그인 화면
class LoginActivity : AppCompatActivity() {
    lateinit var btnLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnLogin = findViewById<Button>(R.id.btnLogin)

        //버튼 누르면 메인 화면 진입
        btnLogin.setOnClickListener {
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun dataSaving() {
        // *** 처음 사용자 로그인 후 현재 사용자 이메일 정보, 현재 날짜 저장 메소드 ***

        var email = "" // 이메일 정보 받아오기
        var nickname = "" // 닉네임 정보 받아오기

        // 로그인 날짜와 로그인 정보 sharedPreference에 저장
        // 오늘 날짜 저장
        val date = LocalDate.now().toString()
        // sharedPreference 'current' 저장
        val sharedPreference = this!!.getSharedPreferences("current", Context.MODE_PRIVATE)
        // sharedPreference 수정
        val editor = sharedPreference.edit()
        // sharedPreference에 현재 날짜 입력(YYYY-MM-DD)
        editor.putString("date", date)
        // sharedPreference에 현재 사용자 이메일 입력
        editor.putString("email", email)
        // sharedPreference에 현재 사용자 닉네임 입력
        editor.putString("nickname", nickname)
        // 저장
        editor.apply()

        val dbManager = DBManager(this)
        dbManager.newUser(email, nickname, "1234")
    }
}