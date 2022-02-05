package com.example.bottnav

import android.content.Context
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bottnav.databinding.ActivityMainBinding
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    // 메인 액티비티

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private lateinit var homeFrag: HomeFragment
    private lateinit var menu1Frag: Menu1Fragment
    private lateinit var menu2Frag: Menu2Fragment
    private lateinit var settingsFrag: SettingsFragment

    private lateinit var bottomNav: BottomNavigationView
    private lateinit var mPlayer: MediaPlayer
    private var pausePos: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        homeFrag = HomeFragment()
        menu1Frag = Menu1Fragment()
        menu2Frag = Menu2Fragment()
        settingsFrag = SettingsFragment()

        supportFragmentManager.beginTransaction().replace(R.id.bottom_container, homeFrag).commit()

        bottomNav = findViewById(R.id.bottom_navigation)
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                //홈 메뉴를 선택한 경우
                R.id.nav_home -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.bottom_container, homeFrag).commit()
                }
                //도전과제 메뉴를 선택한 경우
                R.id.nav_menu1 -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.bottom_container, menu1Frag).commit()
                }
                //기록 메뉴를 선택한 경우
                R.id.nav_menu2 -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.bottom_container, menu2Frag).commit()
                }
                //설정 메뉴를 선택한 경우
                R.id.nav_settings -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.bottom_container, settingsFrag).commit()
                }
            }
            true
        }

        // 배경음악 설정
        // 저장된 음량 크기 불러와 설정
        val sharedPreference = this.getSharedPreferences("current", Context.MODE_PRIVATE)
        val volume = (sharedPreference.getInt("volume", 0).toDouble() / 10).toFloat()

        mPlayer = MediaPlayer.create(this, R.raw.song1)
        mPlayer.isLooping = true
        mPlayer.setVolume(volume, volume)

        mStart()
    }

    fun mStop() {
        mPlayer.stop()
    }

    fun mStart() {
        if (!mPlayer.isPlaying) {  //실행중이지 않은 상태
            mPlayer.seekTo(pausePos)
            mPlayer.start()
        }
    }

    fun mPause() {
        mPlayer.pause()
        pausePos = mPlayer.currentPosition
    }

    // 음악 볼륨 조절
    fun setMvolume(value: Float) {
        mPlayer.setVolume(value, value)
        // sharedPreference 수정
        val sharedPreference = this.getSharedPreferences("current", Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.remove("volume")
        editor.putFloat("volume", value)
        editor.apply()
    }

    //실행 중인 경우 true반환
    fun isplaying(): Boolean {

        return mPlayer.isPlaying
    }

    // fragment 전환 메소드
    fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.bottom_container, fragment)
        fragmentTransaction.addToBackStack("$fragment");
        fragmentTransaction.commit()
    }

    fun replaceFragmentExit(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.bottom_container, fragment)
        fragmentTransaction.commit()
    }

    //액티비티가 소멸될 때 음악이 멈추도록
    override fun onDestroy() {
        super.onDestroy()
        mPlayer.stop()
    }
}