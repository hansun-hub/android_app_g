package com.example.bottnav

import android.annotation.SuppressLint
import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast

class Menu2DetailActivity : AppCompatActivity() {
    // menu2 기록 - 지난 기록을 디테일하게 볼 수 있는 Activity

    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu2_detail)

        val dbManager = DBManager(this)

        //연결시켜줌
        val tvTitle = findViewById<TextView>(R.id.menu2Detail_tvSetTitle)
        val tvDate = findViewById<TextView>(R.id.menu2Detail_tvSetDate)
        val tvContents = findViewById<TextView>(R.id.menu2Detail_tvSetContent)
        val tvselected = findViewById<TextView>(R.id.menu2Detail_tvSetMission)
        val detailRatingBar = findViewById<RatingBar>(R.id.Menu2Detail_SetRatingBar)
        val menu2Detail_btnBack = findViewById<Button>(R.id.menu2Detail_btnBack)
        val menu2Detail_btnErase: Button = findViewById(R.id.menu2Detail_btnErase)

        //intent로 값을 받아옴
        val title = intent.getStringExtra("title")
        val date = intent.getStringExtra("date")
        val contents = intent.getStringExtra("contents")
        val selected = intent.getStringExtra("selectedChallenge")
        val rate = intent.getIntExtra("rate", 0).toFloat()
        //val curPos = intent.getIntExtra("curPos",0)//삭제요망

        //text와 rating을 받아온 값으로 지정해줌
        tvTitle.text = title
        tvDate.text = date
        tvContents.text = contents
        tvselected.text = selected
        detailRatingBar.rating = rate
        detailRatingBar.setIsIndicator(true)
        menu2Detail_btnBack.setOnClickListener {
            onBackPressed()
        }
        menu2Detail_btnErase.setOnClickListener {
            //Toast.makeText(this, "메뉴 클릭", Toast.LENGTH_SHORT).show() //삭제요망
            val builder = AlertDialog.Builder(this)
            builder.setTitle("삭제하시겠습니까?")
                .setPositiveButton(getString(R.string.answer_yes)) { dialog, id ->
                    // DB에서 삭제 진행
                    dbManager.delDiary(date)
                    Toast.makeText(this, "소감이 삭제되었습니다.", Toast.LENGTH_SHORT).show()
                    finish()
                }
                .setNegativeButton(getString(R.string.answer_cancel)) { dialog, id ->
                    // 취소버튼
                }
            // 다이얼로그를 띄워주기
            builder.show()
        }
    }
}