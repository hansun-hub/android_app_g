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
        sqlDB.execSQL("INSERT INTO USERS VALUES (\"$email\", \"$nickname\", \"$password\");")
        sqlDB.close()
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

    public fun getTips(type: String): ArrayList<String>? {
        // 팁/경고 반환

        when (type) {
            "tip" -> {
                var tips = ArrayList<String>(R.array.TIPS)

                return tips
            }
            "warn" -> {
                var warnings = ArrayList<String>(R.array.WARNINGS)

                return warnings
            }
        }

        return null
    }

    public fun getChallenges(period: String): ArrayList<String>? {
        // 미션 배열 반환(함수 호출 시 인자에 따라)

        when (period) {
            "monthly" -> {
                var monthly = ArrayList<String>(R.array.CHALLENGES_M)

                return monthly
            }
            "weekly" -> {
                var weekly = ArrayList<String>(R.array.CHALLENGES_W)

                return weekly
            }
            "daily" -> {
                var daily = ArrayList<String>(R.array.CHALLENGES_D)

                return daily
            }
            "all" -> {
                var all = ArrayList<String>(R.array.CHALLENGES_M + R.array.CHALLENGES_W + R.array.CHALLENGES_D)

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

    public fun addDiary(date: String, title: String, contents: String, score: Int, selected_challenge: String) {
        // DB에 다이어리 추가

        sqlDB = dbHelper.writableDatabase
        // 달성정보 DB에 추가
        sqlDB.execSQL("INSERT INTO \'DIARY_$email\' VALUES ('$date', '$title', '$contents', '$score', '$selected_challenge')")
        sqlDB.close()
    }
}