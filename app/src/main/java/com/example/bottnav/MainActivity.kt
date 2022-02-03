package com.example.bottnav

import android.content.Context
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import android.widget.Button
import com.example.bottnav.databinding.ActivityMainBinding
import android.widget.CalendarView
import android.widget.DatePicker
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog

//메인 화면
class MainActivity : AppCompatActivity() {


    //김한선 추가
    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }


    lateinit var homeFrag: HomeFragment
    lateinit var menu1Frag: Menu1Fragment
    lateinit var menu2Frag: Menu2Fragment
    lateinit var settingsFrag: SettingsFragment

    lateinit var bottomNav: BottomNavigationView
    lateinit var mPlayer : MediaPlayer
    var pausePos : Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        //김한선 수정함
        setContentView(binding.root)

        homeFrag = HomeFragment()
        menu1Frag = Menu1Fragment()
        menu2Frag = Menu2Fragment()
        settingsFrag = SettingsFragment()

        supportFragmentManager.beginTransaction().replace(R.id.bottom_container, homeFrag).commit()

        bottomNav = findViewById(R.id.bottom_navigation)
        bottomNav.setOnItemSelectedListener { item ->
            when(item.itemId) {  //프래그먼트가 유지되어야함
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

        // 배경음악 설정
        // 저장된 음량 크기 불러와 설정
        val sharedPreference = this.getSharedPreferences("current", Context.MODE_PRIVATE)
        val volume = (sharedPreference.getInt("volume", 0).toDouble()/10).toFloat()
        mPlayer = MediaPlayer.create(this, R.raw.song1)
        mPlayer.isLooping = true
        mPlayer.setVolume(volume, volume)
        Mstart()


        //다이얼로그 생성
        /*
        var builder = AlertDialog.Builder(this)
        var view = layoutInflater.inflate(R.layout.dialog_character,null)

        var btnCancel = view.findViewById<Button>(R.id.btnCancel)
        var btnShare = view.findViewById<Button>(R.id.btnShare)

        builder.create()
        builder.setView(view)
        builder.show()
        btnCancel.setOnClickListener {

        }*/

    }

    fun goMenu2(){
        val Menu2FragmentFragment = Menu2Fragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.bottom_container, Menu2FragmentFragment) //FrameLayout은 표시 될 곳
        transaction.addToBackStack("write") //뒤로가는 것 구현
        transaction.commit()
    }



   
    override fun onBackPressed() {
        super.onBackPressed()
    }

    fun goBack(){
        onBackPressed()
    }

    fun Mstop(){
        mPlayer.stop()
    }

    fun Mstart(){
        if(!mPlayer.isPlaying){  //실행중이지 않은 상태
            mPlayer.seekTo(pausePos)
            mPlayer.start()
        }
    }

    fun Mpause(){
        if(mPlayer!=null){
            mPlayer.pause()
            pausePos = mPlayer.currentPosition
        }
    }

    // 음악 볼륨 조절
    fun setMvolume(value: Float){
        mPlayer.setVolume(value, value)
        // sharedPreference 수정
        val sharedPreference = this!!.getSharedPreferences("current", Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.remove("volume")
        editor.putFloat("volume", value)
        editor.apply()
    fun ispalying(): Boolean {
        var play : Boolean = true
        play = mPlayer.isPlaying
        return play   //실행 중인 경우 true반환

    }

    fun goEditTodo(){
        val editTodoFragment = EditTodoFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.bottom_container, editTodoFragment)
        transaction.addToBackStack("editTodo")
        transaction.commit()
    }

    // fragment 전환 메소드
    public fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.bottom_container, fragment)
        fragmentTransaction.addToBackStack("$fragment");
        fragmentTransaction.commit()
    }


    override fun onStop() {  //액티비티가 더 이상 사용자에게 보여지지 않을 때
        super.onStop()
        mPlayer.stop()   //음악 멈춤
    }

}