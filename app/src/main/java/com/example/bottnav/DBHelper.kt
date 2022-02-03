package com.example.bottnav

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context): SQLiteOpenHelper(context, "database1", null, 1) {

    var user_email = context!!.getSharedPreferences("current", Context.MODE_PRIVATE).getString("email", "")

    override fun onCreate(db: SQLiteDatabase?) {
        // 사용자 정보 table 생성
        db!!.execSQL("CREATE TABLE IF NOT EXISTS USERS (email CHAR(20), nickname CHAR(15), password TEXT, level INTEGER);")
        // 미션 달성 정보 table 생성

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS USERS")
        db!!.execSQL("DROP TABLE IF EXISTS \"ACHIEVE_$user_email\"")
        db!!.execSQL("DROP TABLE IF EXISTS \"DIARY_$user_email\"")

        onCreate(db)
    }

}