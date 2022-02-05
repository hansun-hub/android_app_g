package com.example.bottnav

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RatingBar
import android.widget.TextView

//지난 기록을 디테일하게 볼 수 있는 Activity
class menu2_DetailActivity : AppCompatActivity() {

    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu2__detail)

        val dbManager = DBManager(this)

        //연결시켜줌
        var tvTitle = findViewById<TextView>(R.id.menu2Detail_tvSetTitle)
        var tvDate = findViewById<TextView>(R.id.menu2Detail_tvSetDate)
        var tvContents = findViewById<TextView>(R.id.menu2Detail_tvSetContent)
        var tvselected = findViewById<TextView>(R.id.menu2Detail_tvSetMission)
        var DetailRatingBar = findViewById<RatingBar>(R.id.Menu2Detail_SetRatingBar)
        var menu2Detail_btnBack = findViewById<Button>(R.id.menu2Detail_btnBack)
        var menu2Detail_btnErase: Button = findViewById(R.id.menu2Detail_btnErase)

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
        DetailRatingBar.rating = rate
        DetailRatingBar.setIsIndicator(true)
        menu2Detail_btnBack.setOnClickListener {
            onBackPressed()
        }
        menu2Detail_btnErase.setOnClickListener {
            //Toast.makeText(this, "메뉴 클릭", Toast.LENGTH_SHORT).show() //삭제요망
            val builder = AlertDialog.Builder(this)
            builder.setTitle("삭제하시겠습니까?")
                    .setPositiveButton("확인", DialogInterface.OnClickListener { dialog, id ->
                        // DB에서 삭제 진행
                        dbManager.delDiary(date)
                        finish()
                    })
                    .setNegativeButton("취소", DialogInterface.OnClickListener { dialog, id ->
                        // 취소버튼
                    })
            // 다이얼로그를 띄워주기
            builder.show()
        }
    }
}