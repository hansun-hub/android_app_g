package com.example.bottnav

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class RegisterActivity : AppCompatActivity() {

    lateinit var editNickName : EditText
    lateinit var editId : EditText
    lateinit var btnDouCheck : Button
    lateinit var editPwd : EditText
    lateinit var editPwdCheck : EditText
    lateinit var btnRegister : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        editNickName = findViewById(R.id.editNickname)

    }
}