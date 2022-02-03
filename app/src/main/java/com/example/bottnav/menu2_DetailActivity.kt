package com.example.bottnav

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast

class menu2_DetailActivity : AppCompatActivity() {



    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu2__detail)

        var mainActivity: MainActivity = MainActivity()
        val dbManager = DBManager(this)

        //lateinit var menu2Frag: Menu2Fragment
        //menu2Frag = Menu2Fragment()
        lateinit var homeFrag: HomeFragment
        homeFrag = HomeFragment()
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
            builder.setTitle("삭제하시겠습니까?").setPositiveButton("확인",DialogInterface.OnClickListener { dialog, id ->
                dbManager.delDiary(date)
                onBackPressed()
                //val writeFragment = WriteFragment()
                //mainActivity.replaceFragment(writeFragment)
                //val writeFragment = WriteFragment()
                //(activity as MainActivity).replaceFragment(writeFragment)
                //supportFragmentManager.beginTransaction().replace(R.id.bottom_container, menu2Frag).commit()
                //mainActivity.goMenu2()
                var intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
                //supportFragmentManager.beginTransaction().replace(R.id.bottom_container, homeFrag).commit()
                //Toast.makeText(this, curPos, Toast.LENGTH_SHORT).show()
                //Toast.makeText(this, "삭제되었습니다.", Toast.LENGTH_SHORT).show()
            //resultText.text = "확인 클릭"
            })
                    .setNegativeButton("취소", DialogInterface.OnClickListener { dialog, id ->
                                //resultText.text = "취소 클릭"
                            })
            // 다이얼로그를 띄워주기
            builder.show()
        }

    }
}
