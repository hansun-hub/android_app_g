package com.example.bottnav

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import kotlin.system.exitProcess


class LoginActivity : AppCompatActivity() {
    // 로그인

    private lateinit var login_btnLogin: Button
    private lateinit var login_regText: TextView
    private lateinit var login_editEmail: EditText
    private lateinit var login_editPassword: EditText
    private lateinit var dbHelper: DBHelper
    private lateinit var sqlDB: SQLiteDatabase
    lateinit var dbManager: DBManager

    private lateinit var email: String
    private lateinit var password: String
    lateinit var nickname: String
    private var level: Int = 0
    private var aimLevel: Int = 10

    @SuppressLint("Range")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        login_btnLogin = findViewById(R.id.login_btnLogin)
        login_regText = findViewById(R.id.login_regText)
        login_editEmail = findViewById(R.id.login_editEmail)
        login_editPassword = findViewById(R.id.login_editPassword)

        val pref: SharedPreferences = getSharedPreferences("current", Context.MODE_PRIVATE)

        dbHelper = DBHelper(this)
        dbManager = DBManager(this)

        //이미 로그인한 상태라면 접속 날짜 확인 후 바로 메인 화면 진입
        if (pref.getString("email", "") != "") {
            if (pref.getString("date", "") != LocalDate.now().toString()) {
                val editor = pref.edit()
                editor.remove("date")
                editor.putString("date", LocalDate.now().toString())
                editor.apply()
            }

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        //회원가입창에서 값 받아옴
        val getIntent: Intent = intent
        login_editEmail.setText(getIntent.getStringExtra("email"))
        login_editPassword.setText(getIntent.getStringExtra("password"))


        //버튼 누르면 메인 화면 진입
        login_btnLogin.setOnClickListener {
            email = login_editEmail.text.toString()
            password = login_editPassword.text.toString()

            //이메일이나 비밀번호가 비어있는 경우
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "빈 칸을 채워주세요.", Toast.LENGTH_SHORT).show()
            } else {
                sqlDB = dbHelper.writableDatabase

                //아이디 확인
                var cursor =
                    sqlDB.rawQuery("SELECT email FROM USERS WHERE email='$email'", null)

                if (cursor.count != 1) {    //중복 아이디가입 허용을 안했기 때문 0인 경우에 해당(회원아님)
                    Toast.makeText(this, getString(R.string.login_fail_email), Toast.LENGTH_SHORT)
                        .show()
                    login_editEmail.text = null
                    login_editPassword.text = null
                } else {
                    //비밀번호 확인
                    cursor = sqlDB.rawQuery(
                        "SELECT password FROM USERS WHERE email = '$email'",
                        null
                    )
                    cursor.moveToNext()
                    if (password != cursor.getString(0)) {  //비밀번호가 맞지 않을 경우
                        Toast.makeText(
                            this,
                            getString(R.string.login_fail_password),
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {  //로그인 성공
                        Toast.makeText(
                            this,
                            getString(R.string.login_success),
                            Toast.LENGTH_SHORT
                        ).show()
                        //닉네임,레벨 받아오는 쿼리문
                        cursor = sqlDB.rawQuery(
                            "SELECT * FROM USERS WHERE email = '$email'",
                            null
                        )
                        cursor.moveToNext()
                        nickname = cursor.getString(cursor.getColumnIndex("nickname"))
                        level = cursor.getInt(cursor.getColumnIndex("level"))

                        //레벨에 따라 목표 레벨 설정
                        when (level) {
                            in 0..9 -> {
                                aimLevel = 10
                            }
                            in 10..29 -> {
                                aimLevel = 30
                            }
                            in 30..59 -> {
                                aimLevel = 60
                            }
                            in 60..100 -> {
                                aimLevel = 100
                            }
                        }

                        dataSaving(email, nickname, aimLevel) //이 버튼 눌리는 순간을 기억

                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)

                        finish()
                    }
                }
                cursor.close()
                sqlDB.close()
            }

        }

        //회원가입 누르면 회원가입 창으로 이동
        login_regText.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun dataSaving(email: String, Nick: String, aimLev: Int) {
        // 로그인 날짜와 로그인 정보 shared Preferences에 저장

        // 오늘 날짜 저장
        val date = LocalDate.now().toString()
        // sharedPreference 'current' 저장
        val sharedPreference = this.getSharedPreferences("current", Context.MODE_PRIVATE)
        // sharedPreference 수정
        val editor = sharedPreference.edit()
        // sharedPreference에 현재 날짜 입력(YYYY-MM-DD)
        editor.putString("date", date)
        // sharedPreference에 현재 사용자 이메일 입력
        editor.putString("email", email)
        // sharedPreference에 현재 사용자 닉네임 입력
        editor.putString("nickname", Nick)
        // 현재 음악 볼륨 저장
        editor.putInt("volume", 10)
        //// sharedPreference에 현재 사용자 목표레벨 입력
        editor.putInt("aimLevel", aimLev)
        // 저장
        editor.apply()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
        System.runFinalization()
        exitProcess(0)
    }
}