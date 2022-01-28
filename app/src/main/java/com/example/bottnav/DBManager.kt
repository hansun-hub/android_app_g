package com.example.bottnav

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Build
import androidx.annotation.RequiresApi

class DBManager(context: Context) {

    val sharedPreference = context.getSharedPreferences("current", Context.MODE_PRIVATE)
    private val email = sharedPreference.getString("email", "")
    private val date = sharedPreference.getString("date", "")

    private val thisContext: Context? = context
    private val dbHelper = DBHelper(context)
    lateinit var sqlDB: SQLiteDatabase
    lateinit var cursor: Cursor

    @RequiresApi(Build.VERSION_CODES.O)
    public fun newUser(email: String, nickname: String, password: String) {
        // 새로운 유저 생성 메소드 - 회원가입 후 실행
        sqlDB = dbHelper.writableDatabase
        sqlDB.execSQL("INSERT INTO USERS VALUES (\"$email\", \"$nickname\", \"$password\", 0);")
        sqlDB.close()
    }

    @SuppressLint("Range")
    public fun getPassword(): String {
        // 비밀번호 가져오기

        var password: String = ""

        sqlDB = dbHelper.readableDatabase
        cursor = sqlDB.rawQuery("SELECT * FROM USERS WHERE email=\'$email\';", null)

        if (cursor.count == 1) {
            cursor.moveToFirst()
            password = cursor.getString(cursor.getColumnIndex("password"))
        }

        cursor.close()
        sqlDB.close()
        dbHelper.close()

        return password
    }

    public fun delUser() {
        // 회원탈퇴 시 사용자 정보 삭제
        sqlDB = dbHelper.writableDatabase
        sqlDB.execSQL("DELETE FROM USERS WHERE email=\"$email\";")
        sqlDB.execSQL("DROP TABLE IF EXISTS \"ACHIEVE_$email\"")
        sqlDB.execSQL("DROP TABLE IF EXISTS \"DIARY_$email\"")
    }

    @SuppressLint("Range")
    public fun getNickname(): String {
        // 닉네임 가져오기

        var nickname: String = ""

        sqlDB = dbHelper.readableDatabase
        cursor = sqlDB.rawQuery("SELECT * FROM USERS WHERE email=\'$email\';", null)

        if (cursor.count == 1) {
            cursor.moveToFirst()
            nickname = cursor.getString(cursor.getColumnIndex("nickname"))
        }

        cursor.close()
        sqlDB.close()
        dbHelper.close()

        return nickname
    }

    public fun getTips(type: String): Array<out String>? {
        // 팁/경고 반환

        when (type) {
            "tip" -> {
                var tips = thisContext!!.resources.getStringArray(R.array.TIPS)

                return tips
            }
            "warn" -> {
                var warnings = thisContext!!.resources.getStringArray(R.array.WARNINGS)

                return warnings
            }
        }

        return null
    }

    public fun getChallenges(period: String): MutableList<String>? {
        // 미션 배열 반환(함수 호출 시 인자에 따라)

        when (period) {
            "monthly" -> {
                var monthly = ArrayList<String>(R.array.CHALLENGES).subList(0, 6)

                return monthly
            }
            "weekly" -> {
                var weekly = ArrayList<String>(R.array.CHALLENGES).subList(6, 14)

                return weekly
            }
            "daily" -> {
                var daily = ArrayList<String>(R.array.CHALLENGES).subList(14, 25)

                return daily
            }
            "all" -> {
                var all = ArrayList<String>(R.array.CHALLENGES)

                return all
            }
        }

        return null
    }

    public fun addCustomChallenge(contents: String, period: String) {
        // DB에 사용자 설정 미션 추가

        sqlDB = dbHelper.writableDatabase
        // 달성정보 DB에 추가
        sqlDB.execSQL("INSERT INTO \'ACHIEVE_$email\' VALUES ('$date', '$period', 'N')")
        sqlDB.close()
    }

    @SuppressLint("Range")
    public fun getSelectedChallenges(date: String, title: String): List<String>? {
        // DB에서 '선택된 미션' 배열로 반환

        sqlDB = dbHelper.readableDatabase
        cursor = sqlDB.rawQuery("SELECT * FROM USERS WHERE email=\'$email\';", null)

        if (cursor.count == 1) {
            cursor.moveToFirst()
            val selected = cursor.getString(cursor.getColumnIndex("selected")).split(',')

            cursor.close()
            sqlDB.close()
            dbHelper.close()

            return selected
        }

        return null
    }

    public fun addDiary(date: String, title: String, contents: String, score: Int, selected_challenge: String) {
        // DB에 다이어리 추가

        sqlDB = dbHelper.writableDatabase
        // 달성정보 DB에 추가
        sqlDB.execSQL("INSERT INTO \'DIARY_$email\' VALUES ('$date', '$title', '$contents', '$score', '$selected_challenge')")
        sqlDB.close()
    }
}