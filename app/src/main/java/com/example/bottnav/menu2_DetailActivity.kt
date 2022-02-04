package com.example.bottnav

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast

class menu2_DetailActivity : AppCompatActivity() {
    var volume: Float = 0f


    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu2__detail)

        val dbManager = DBManager(this)
        val sharedPreference = this.getSharedPreferences("current", Context.MODE_PRIVATE)
        volume = (sharedPreference.getInt("volume", 0).toDouble()/10).toFloat()

        var tvTitle = findViewById<TextView>(R.id.tvTitle)
        var tvDate = findViewById<TextView>(R.id.tvDate)
        var tvContents = findViewById<TextView>(R.id.tvContents)
        var tvselected = findViewById<TextView>(R.id.tvSelected)
        var DetailRatingBar = findViewById<RatingBar>(R.id.DetailRatingBar)
        var menu2Detail_btnBack = findViewById<Button>(R.id.menu2Detail_btnBack)
        var menu2Detail_btnErase: Button = findViewById(R.id.menu2Detail_btnErase)

        val title = intent.getStringExtra("title")
        val date = intent.getStringExtra("date")
        val contents = intent.getStringExtra("contents")
        val selected = intent.getStringExtra("selectedChallenge")
        val rate = intent.getIntExtra("rate",0).toFloat()
        val curPos = intent.getIntExtra("curPos",0)


        tvTitle.text = title
        tvDate.text = date
        tvContents.text = contents
        tvselected.text = selected
        DetailRatingBar.setRating(rate)
        menu2Detail_btnBack.setOnClickListener {
            onBackPressed()
        }
        menu2Detail_btnErase.setOnClickListener {
            //Toast.makeText(this, "메뉴 클릭", Toast.LENGTH_SHORT).show()
            val builder = AlertDialog.Builder(this)
            builder.setTitle("삭제하시겠습니까?")
                    .setPositiveButton("확인",DialogInterface.OnClickListener { dialog, id ->
                        // 삭제 진행
                        dbManager.delDiary(date)
                        finish()
                    })
                    .setNegativeButton("취소", DialogInterface.OnClickListener { dialog, id ->
                        // 삭제 취소
                        finish()
                    })
            // 다이얼로그를 띄워주기
            builder.show()
        }

    }

}