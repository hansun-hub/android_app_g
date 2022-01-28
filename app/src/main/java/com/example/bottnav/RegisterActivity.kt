package com.example.bottnav

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi

class RegisterActivity : AppCompatActivity() {

    lateinit var editNickName : EditText
    lateinit var editId : EditText
    lateinit var btnDouCheck : Button
    lateinit var editPwd : EditText
    lateinit var editPwdCheck : EditText
    lateinit var btnRegister : Button
    lateinit var dbManager: DBManager

    lateinit var dbHelper: DBHelper
    lateinit var sqlDB: SQLiteDatabase
    lateinit var cursor: Cursor

    lateinit var email : String
    lateinit var nickname : String
    lateinit var password : String
    lateinit var passwordCheck : String

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        editNickName = findViewById(R.id.editNickname)
        editId = findViewById(R.id.editId)
        btnDouCheck = findViewById(R.id.btnDouCheck)
        editPwd = findViewById(R.id.editPwd)
        editPwdCheck = findViewById(R.id.editPwdcheck)
        btnRegister = findViewById(R.id.btnRegister)

        dbHelper = DBHelper(this)
        dbManager= DBManager(this)


        //회원가입 확인 코드
        btnDouCheck.setOnClickListener {
            email = editId.text.toString()
            sqlDB = dbHelper.writableDatabase
            if(email.length>20){  //20자리까지만 입력 가능
                Toast.makeText(this, "이메일은 20자리까지 가능합니다. ",Toast.LENGTH_SHORT).show()
            }
            else {
                cursor = sqlDB.rawQuery("SELECT email FROM USERS WHERE email=\'$email\';", null)
                if (cursor.count != 0) {  //이미 존재하는 회원인 경우
                    Toast.makeText(this, "이미 가입된 회원입니다.", Toast.LENGTH_SHORT).show()
                    editId.setText(null) //이메일 초기화
                } else {  //20자리를 넘지 않고+존재하지 않는 회원인 경우
                    Toast.makeText(this, "사용 가능한 이메일입니다.", Toast.LENGTH_SHORT).show()

                    //등록 버튼 클릭시
                    btnRegister.setOnClickListener {
                        nickname = editNickName.text.toString()
                        password = editPwd.text.toString()
                        passwordCheck = editPwdCheck.text.toString()
                        //비어있는 칸이 있는지 확인
                        if (email.length == 0 || nickname.length == 0 || password.length == 0) {
                            Toast.makeText(this, "빈 칸을 채워주세요.", Toast.LENGTH_SHORT).show()
                        } else if (nickname.length > 15) {
                            Toast.makeText(this, "닉네임은 15자리까지 가능합니다. ", Toast.LENGTH_SHORT).show()
                        } else {
                            //비밀번호 같은지 확인 (string이 같은지equals사용하면 될듯)
                            if (password.equals(passwordCheck)) {
                                //회원가입 코드
                                dbManager.newUser(email, nickname, password)
                                Toast.makeText(this, "회원 가입 성공.", Toast.LENGTH_SHORT).show()
                                //db저장 완료된 상태. 로그인액티비티로 이동
                                var intent = Intent(this, LoginActivity::class.java)
                                intent.putExtra("email", email)
                                intent.putExtra("password", password)
                                startActivity(intent)
                                finish()

                            } else {
                                Toast.makeText(this, "비밀번호가 같지 않습니다.", Toast.LENGTH_SHORT).show()
                            }
                            //Toast.makeText(this, "문제.",Toast.LENGTH_SHORT).show()
                        }


                    }
                }
            }
        }

        btnRegister.setOnClickListener {
            Toast.makeText(this, "중복 확인을 먼저 해주세요",Toast.LENGTH_SHORT).show()
        }

    }
}