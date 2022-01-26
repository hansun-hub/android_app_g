package com.example.bottnav

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
}