package com.example.bottnav

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Build
import android.provider.ContactsContract
import android.widget.Toast
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashSet

class DBManager(context: Context) {

    val sharedPreference = context.getSharedPreferences("current", Context.MODE_PRIVATE)
    private val email = sharedPreference.getString("email", "")
    private val date = sharedPreference.getString("date", "")

    private val thisContext: Context? = context
    private val dbHelper = DBHelper(context)
    lateinit var sqlDB: SQLiteDatabase
    lateinit var cursor: Cursor

    @RequiresApi(Build.VERSION_CODES.O)
    public fun newUser(user_email: String, nickname: String, password: String) {
        // 새로운 유저 생성 메소드 - 회원가입 후 실행
        sqlDB = dbHelper.writableDatabase
        sqlDB.execSQL("INSERT INTO USERS VALUES (\"$user_email\", \"$nickname\", \"$password\", 0);")

        // 미션 달성 정보 table 생성
        sqlDB.execSQL("CREATE TABLE IF NOT EXISTS \"ACHIEVE_$user_email\" (date TEXT, type CHAR, i INTEGER, is_achieved CHAR);")
        // 소감 table 생성
        sqlDB.execSQL("CREATE TABLE IF NOT EXISTS \"DIARY_$user_email\" (data TEXT, title TEXT, contents TEXT, score INTEGER, selected TEXT)")

        setAchieveList(user_email)
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

    public fun setNickname(newNickname: String) {
        sqlDB = dbHelper.writableDatabase
        sqlDB.execSQL("UPDATE USERS SET nickname='$newNickname' WHERE email='$email';")

        sqlDB.close()
        dbHelper.close()
    }

    public fun setLevel(newLevelup: String) {   //레벨 변경=>menu1Fragment에서 사용하기
        sqlDB = dbHelper.writableDatabase
        sqlDB.execSQL("UPDATE USERS SET level='$newLevelup' WHERE email='$email';")

        sqlDB.close()
        dbHelper.close()
    }

    @SuppressLint("Range")
    public fun getLevel(): Int {
        // 레벨 가져오기

        var levelCount : Int = 0

        sqlDB = dbHelper.readableDatabase
        cursor = sqlDB.rawQuery("SELECT * FROM USERS WHERE email=\'$email\';", null)

        if (cursor.count == 1) {
            cursor.moveToFirst()
            levelCount = cursor.getInt(cursor.getColumnIndex("level"))
        }

        cursor.close()
        sqlDB.close()
        dbHelper.close()

        return levelCount
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

    public fun getChallenges(period: String): Array<out String>? {
        // 미션 배열 반환(함수 호출 시 인자에 따라)

        when (period) {
            "monthly" -> {
                var monthly = thisContext!!.resources.getStringArray(R.array.CHALLENGES).sliceArray(0..5)

                return monthly
            }
            "weekly" -> {
                var weekly = thisContext!!.resources.getStringArray(R.array.CHALLENGES).sliceArray(6..13)

                return weekly
            }
            "daily" -> {
                var daily = thisContext!!.resources.getStringArray(R.array.CHALLENGES).sliceArray(14.. 25)
                //var daily = thisContext!!.resources.getStringArray(R.array.CHALLENGES).sliceArray(14..24)

                return daily
            }
            "all" -> {
                var all = thisContext!!.resources.getStringArray(R.array.CHALLENGES)

                return all
            }
        }

        return null
    }

    public fun getChallenge(index: Int): String? {
        // index에 따른 미션 반환
        return getChallenges("all")!!.get(index)
    }

    public fun addCustomChallenge(findDate: String, contents: String) {
        // DB에 사용자 설정 미션 추가

        sqlDB = dbHelper.writableDatabase

        // 해당 날짜의 미션 개수 가져오기

        cursor = sqlDB.rawQuery("SELECT * FROM \'ACHIEVE_$email\' WHERE date='$findDate';", null)
        var i = 0
        while (cursor.moveToNext())
            i++

        // 달성정보 DB에 추가
        sqlDB.execSQL("INSERT INTO \'ACHIEVE_$email\' VALUES('$findDate', 'D', ${i + 25}, 'N');")

        cursor.close()
        sqlDB.close()
    }

    @SuppressLint("Range")
    public fun getSelectedChallenges(findDate: String, title: String): List<String>? {
        // DB에서 '선택된 미션' 배열로 반환

        sqlDB = dbHelper.readableDatabase
        cursor = sqlDB.rawQuery("SELECT * FROM \'ACHIEVE_$email\' WHERE date='$findDate' and title='$title';", null)

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

    @RequiresApi(Build.VERSION_CODES.O)
    public fun setAchieveList(user_email: String) {
        // 첫 회원가입 시 달성할 미션 내역 생성(월 2개, 주 2개, 일 4개)

        sqlDB = dbHelper.writableDatabase

        var dateToday = LocalDate.now()

        // 중복되지 않게 선택하기 위해 Set 사용
        val random = Random()
        var monthly = HashSet<Int>()
        var weekly = ArrayList<HashSet<Int>>(5)
        var daily = ArrayList<HashSet<Int>>(30)

        // 달 별 미션 생성
        while (monthly.size < 2) {
            var i = random.nextInt(6)
            monthly.add(i)
        }

        // 주 별 미션 생성
        for (n in 0..4) {
            weekly.add(HashSet<Int>())
            while (weekly[n].size < 2) {
                var i = random.nextInt(8) + 6
                weekly[n].add(i)
            }
        }

        // 일 별 미션 생성
        for (n in 0..29) {
            daily.add(HashSet<Int>())
            while (daily[n].size < 4) {
                var i = random.nextInt(11) + 14
                daily[n].add(i)
            }
        }

        // 미션 저장
        for (i in 0..29) {
            for (index in monthly) {
                sqlDB.execSQL("INSERT INTO \'ACHIEVE_${user_email}\' VALUES ('$dateToday', 'M', $index, 'N');")
            }
            for (index in weekly[i/7]) {
                sqlDB.execSQL("INSERT INTO \'ACHIEVE_${user_email}\' VALUES ('$dateToday', 'W', $index, 'N');")
            }
            for (index in daily[i]) {
                sqlDB.execSQL("INSERT INTO \'ACHIEVE_${user_email}\' VALUES ('$dateToday', 'D', $index, 'N');")
            }

            // 날짜 하루 추가
            dateToday = dateToday.plusDays(1)
        }

        sqlDB.close()
    }

    @SuppressLint("Range")
    public fun getTodayChallenges(findDate: String): ArrayList<Int> {
        // 해당 날짜에 배정된 미션 배열 불러오기

        var challenges = ArrayList<Int>()

        sqlDB = dbHelper.readableDatabase
        cursor = sqlDB.rawQuery("SELECT * FROM \'ACHIEVE_$email\' WHERE date='$findDate';", null)

        while(cursor.moveToNext()) {
            challenges.add(cursor.getInt(cursor.getColumnIndex("i")))
        }

        sqlDB.close()
        cursor.close()

        return challenges
    }

    public fun setIsAchieved(i: Int) {
        // 사용자가 미션 달성했을 때 달성정보 수정
        sqlDB = dbHelper.writableDatabase
        sqlDB.execSQL("UPDATE \'ACHIEVE_$email\' SET is_achieved='Y' WHERE date='$date' and i=$i;")
    }

}