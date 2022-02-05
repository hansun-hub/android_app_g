package com.example.bottnav

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, "database1", null, 1) {
    // DB 접근 위한 helper

    // Shared Preferences에서 현재 사용자의 email 정보 가져오기
    var user_email =
        context.getSharedPreferences("current", Context.MODE_PRIVATE).getString("email", "")

    override fun onCreate(db: SQLiteDatabase?) {
        // 사용자 정보 table 생성
        db!!.execSQL("CREATE TABLE IF NOT EXISTS USERS (email CHAR(20), nickname CHAR(15), password TEXT, level INTEGER);")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS USERS")
        db.execSQL("DROP TABLE IF EXISTS \"ACHIEVE_$user_email\"")
        db.execSQL("DROP TABLE IF EXISTS \"DIARY_$user_email\"")

        onCreate(db)
    }

}