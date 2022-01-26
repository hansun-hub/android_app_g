package com.example.bottnav

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate

//로그인 화면
class LoginActivity : AppCompatActivity() {
    lateinit var btnLogin: Button
    lateinit var regText : TextView
    lateinit var editEmail : EditText
    lateinit var editPassword : EditText
    lateinit var myHelper: SQLiteOpenHelper  //일단 sqliteopenhelper형태로 지정해둠(나중에 생성한 클래스로 바꾸기)
    lateinit var sqlDB : SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnLogin = findViewById<Button>(R.id.btnLogin)
        regText = findViewById(R.id.regText)
        editEmail = findViewById(R.id.editEmail)
        editPassword = findViewById(R.id.editPassword)

        var fragmentHome : Fragment

        //버튼 누르면 메인 화면 진입
        btnLogin.setOnClickListener {
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            /*
            var email = editEmail.text.toString()
            var password = editPassword.text.toString()
            sqlDB = myHelper.writableDatabase
            //var cursor : Cursor = sqlDB.rawQuery("SELECT * FROM MemTable WHERE id = '"+email + "' AND pwd = '"+password+"'",null)

            //아이디 확인
            var cursor : Cursor = sqlDB.rawQuery("SELECT id FROM MemTable WHERE id = '"+email +"'",null)

            if(cursor.getCount()!=1){    //중복 아이디가입 허용을 안했기 때문 0인 경우에 해당(회원아님)
                Toast.makeText(this, "존재하지 않는 이메일입니다.\n회원가입을 먼저 진행해주세요",Toast.LENGTH_SHORT).show()
            }
            else{
                //비밀번호 확인
                cursor = sqlDB.rawQuery("SELECT pwd FROM MemTable WHERE id = '"+email+"'",null)
                cursor.moveToNext()
                if(!password.equals(cursor.getString(0))){  //비밀번호가 맞지 않을 경우
                    Toast.makeText(this, "비밀번호가 맞지 않습니다. 다시 시도해주세요.",Toast.LENGTH_SHORT).show()
                }
                else{  //로그인 성공
                    cursor = sqlDB.rawQuery("SELECT nick FROM MemeTable WHERE id = '"+email+"'",null)  //닉네임 받아오는 쿼리문
                    cursor.moveToNext()
                    fragmentHome = HomeFragment.newInstance(cursor.getString(0)) //닉네임을 string타입으로 전달하기

                    var intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
            }
            cursor.close()
            sqlDB.close()*/

        }

        //회원가입 누르면 회원가입 창으로 이동
        regText.setOnClickListener{
            var intent = Intent(this, RegisterActivity::class.java)
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