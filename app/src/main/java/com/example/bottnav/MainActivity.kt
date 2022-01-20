package com.example.bottnav

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView

//메인 화면
class MainActivity : AppCompatActivity() {
    lateinit var homeFrag: HomeFragment
    lateinit var challengeFrag: ChallengeFragment
    lateinit var rewardFrag: RewardFragment
    lateinit var settingsFrag: SettingsFragment

    lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        homeFrag = HomeFragment()
        challengeFrag = ChallengeFragment()
        rewardFrag = RewardFragment()
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
                R.id.nav_challenge -> {
                    supportFragmentManager.beginTransaction().replace(R.id.bottom_container, challengeFrag).commit()
                }
                //보상 탭을 선택한 경우
                R.id.nav_reward -> {
                    supportFragmentManager.beginTransaction().replace(R.id.bottom_container, rewardFrag).commit()
                }
                //설정 탭을 선택한 경우
                R.id.nav_settings -> {
                    supportFragmentManager.beginTransaction().replace(R.id.bottom_container, settingsFrag).commit()
                }
            }

            true
        }
    }
}