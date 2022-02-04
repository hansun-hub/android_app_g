package com.example.bottnav

import android.content.Context
import android.content.SharedPreferences
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_Nick = "nick"  //안쓰면 지우기

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentHome.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    lateinit var home_tvNick : TextView
    lateinit var home_btnMusic : ImageButton
    lateinit var home_tvDo : TextView
    lateinit var home_tvDone : TextView
    lateinit var tvTip : TextView
    lateinit var NICK : String
    lateinit var ivSprout : ImageView
    lateinit var dbManager : DBManager

    var missionDo : Int = 10
    var missionDone : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            //nick = it.getString(ARG_Nick)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view : View = inflater.inflate(R.layout.fragment_home, container, false)

        home_tvNick = view.findViewById(R.id.home_tvNick)
        home_btnMusic = view.findViewById(R.id.home_btnMusic)
        home_tvDo = view.findViewById(R.id.home_tvDo)
        home_tvDone = view.findViewById(R.id.home_tvDone)
        tvTip = view.findViewById(R.id.home_tvTip)
        ivSprout = view.findViewById(R.id.home_ivSprout)  //tvDone수에 따라 이미지 바뀌도록 연결하기

        dbManager = DBManager(view.context)

        //SharedPreferences로 닉네임 가져오기
        val pref : SharedPreferences = requireActivity().getSharedPreferences("current", Context.MODE_PRIVATE)
        NICK = pref.getString("nickname",null).toString()
        home_tvNick.setText(NICK)  //login페이지에서 넘어온 닉네임이 화면에 보임

        missionDo = pref.getInt("aimLevel",0)  //목표 레벨 (sharedPrefereces사용)가져오기
        home_tvDo.setText(missionDo.toString())

        missionDone = dbManager.getLevel()  //현재 레벨 (db 사용)가져오기
        home_tvDone.setText(missionDone.toString())
        //var isplay : Boolean = true

        //음악 버튼
        home_btnMusic.setOnClickListener {
            //실행 중인지 확인하는 코드 추가하기
            if((activity as MainActivity).ispalying()){ //실행 중이라면 중단
                (activity as MainActivity).Mpause()
                home_btnMusic.setImageResource(R.drawable.ic_baseline_music_off_24)
            }
            else{
                (activity as MainActivity).Mstart()
                home_btnMusic.setImageResource(R.drawable.ic_baseline_music_note_24)
            }
        }
        if(!(activity as MainActivity).ispalying()){  //실행 중이지 않은 상태면 음악이 꺼진 버튼이 보임. (프래그먼트끼리 이동시 오류 해결)
            home_btnMusic.setImageResource(R.drawable.ic_baseline_music_off_24)
        }

        //db에서 팁 배열 가져옴
        val tip = dbManager.getTips("tip")

        //랜덤 수 만들기
        var tipSize = (tip!!.size).toInt()
        val random = (0..tipSize-1).random()
        //Toast.makeText(getActivity(), "$random", Toast.LENGTH_SHORT).show()

        //팁 배열에서 랜덤으로 값 가져와서 text가 바뀜
        tvTip.text = tip?.get(random)


        //친구에게 공유(자랑)할 수 있는 dialog 생성
        val dialog =  CharacterDialog(view.context)
        //함수로 묶어둠
        fun popupDialog(resChar : Int){
            dialog.showDialog(resChar) //리소스 명 넣기 R.drawable.~~~
            dialog.setOnClickedListener(object : CharacterDialog.ButtonClickListener{
                //공유버튼 클릭시
                override fun onClicked() {  //CharacterDialogFragment에서 생성한 함수 오버라이드를 통해 사용
                    //바텀시트 나옴
                    val bottomSheet = CharacterBottomSheetFragment()
                    bottomSheet.isCancelable=false  //외부영역 터치로 사라지지 않음
                    bottomSheet.show(childFragmentManager,bottomSheet.tag)
                }
            })
        }

        //missionDo = home_tvDo.text.toString().toInt()
        //missionDone = home_tvDone.text.toString().toInt()  //나중에 db에서 level받아오는 코드로 바꾸기

        //현재 레벨에 따라 이미지 바꿈(프래그먼트간 이동에 값 초기화 방지)
        when(missionDo){
            10 -> {
                ivSprout.setImageDrawable(getResources().getDrawable(R.drawable.sprout))  //캐릭터 이미지 바꿈
            }
            30 -> {
                ivSprout.setImageDrawable(getResources().getDrawable(R.drawable.tree))  //캐릭터 이미지 바꿈
            }
            60 -> {
                ivSprout.setImageDrawable(getResources().getDrawable(R.drawable.appletrees))  //캐릭터 이미지 바꿈
            }
            100 -> {
                ivSprout.setImageDrawable(getResources().getDrawable(R.drawable.forest))  //캐릭터 이미지 바꿈
                //모든 미션을 달성한 경우 (100/100인 경우)
                if(missionDone==100){
                    ivSprout.setImageDrawable(getResources().getDrawable(R.drawable.healthy_earth))  //캐릭터 이미지 바꿈
                }
            }
        }

        //sharedPreference 수정 (목표 개수)
        val editor = pref.edit()
        fun aimLevelChange(aim : Int){
            editor.remove("aimLevel")
            editor.putInt("aimLevel",aim)
            editor.apply()
        }

        //목표 성취 개수를 (레벨이)넘은 경우
        if(missionDone>=missionDo) {

            when (missionDone) {  //성취 개수
                in 10..29 -> {   // 10~29개를 달성한 경우
                    missionDo = 30  //다음 달성 목표
                    aimLevelChange(missionDo)    //목표 개수 수정
                    home_tvDo.text = missionDo.toString()  //달성 목표 화면에 반영
                    ivSprout.setImageDrawable(getResources().getDrawable(R.drawable.tree))  //캐릭터 이미지 바꿈
                    ivSprout.layoutParams.width = 140  //너비 크게 수정
                    popupDialog(R.drawable.tree)
                }
                in 30..59 -> {
                    missionDo = 60  //다음 달성 목표
                    aimLevelChange(missionDo)
                    home_tvDo.text = missionDo.toString()
                    ivSprout.setImageDrawable(getResources().getDrawable(R.drawable.appletrees))
                    popupDialog(R.drawable.appletrees)
                }
                in 60..99 -> {
                    missionDo = 100  //다음 달성 목표
                    aimLevelChange(missionDo)
                    home_tvDo.text = missionDo.toString()
                    ivSprout.setImageDrawable(getResources().getDrawable(R.drawable.forest))
                    popupDialog(R.drawable.forest)
                }
                100 -> {
                    home_tvDo.text = missionDo.toString()
                    ivSprout.setImageDrawable(getResources().getDrawable(R.drawable.healthy_earth))
                    popupDialog(R.drawable.healthy_earth)
                }

            }
        }


        //else{
            //Toast.makeText(getActivity(), "한 일의 수 : $missionDone", Toast.LENGTH_SHORT).show()
        //}
        /*if(tvDo==tvDone){
            var builder = AlertDialog.Builder()
            var dia = layoutInflater.inflate(R.layout.dialog_character,null)
            builder.setView(dia)
        }

        val characterFragment = CharacterDialogFragment()
        characterFragment.show(childFragmentManager,null)*/


        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FragmentHome.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_Nick, param1)
                }
            }
    }
}