package com.example.bottnav

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CalendarView
import android.widget.DatePicker
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

//메인 화면
class MainActivity : AppCompatActivity() {
    lateinit var homeFrag: HomeFragment
    lateinit var menu1Frag: Menu1Fragment
    lateinit var menu2Frag: Menu2Fragment
    lateinit var settingsFrag: SettingsFragment

    lateinit var bottomNav: BottomNavigationView
    lateinit var mPlayer : MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mPlayer = MediaPlayer.create(this, R.raw.song1)
        mPlayer.start()


        homeFrag = HomeFragment()
        menu1Frag = Menu1Fragment()
        menu2Frag = Menu2Fragment()
        settingsFrag = SettingsFragment()

        supportFragmentManager.beginTransaction().replace(R.id.bottom_container, homeFrag).commit()

        bottomNav = findViewById(R.id.bottom_navigation)
        bottomNav.setOnItemSelectedListener { item ->
            when(item.itemId) {
                //홈 탭을 선택한 경우
                R.id.nav_home -> {
                    supportFragmentManager.beginTransaction().replace(R.id.bottom_container, homeFrag).commit()
                }
                //도전과제 탭을 선택한 경우
                R.id.nav_menu1 -> {
                    supportFragmentManager.beginTransaction().replace(R.id.bottom_container, menu1Frag).commit()
                }
                //보상 탭을 선택한 경우
                R.id.nav_menu2 -> {
                    supportFragmentManager.beginTransaction().replace(R.id.bottom_container, menu2Frag).commit()
                }
                //설정 탭을 선택한 경우
                R.id.nav_settings -> {
                    supportFragmentManager.beginTransaction().replace(R.id.bottom_container, settingsFrag).commit()
                }
            }

            true
        }
    }
    fun Mstop(){
        mPlayer.stop()
    }
}