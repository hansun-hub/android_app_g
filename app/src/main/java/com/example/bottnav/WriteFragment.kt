package com.example.bottnav

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
//import com.example.bottnav.databinding.FragmentWriteBinding

//import com.example.bottnav.databinding.FragmentWriteBinding

//위에 import중에 삭제 요먕

class WriteFragment : Fragment() {

    //lateinit var binding: FragmentWriteBinding //삭제요망
    lateinit var mainActivity: MainActivity
    lateinit var dbHelper: DBHelper //삭제요망
    //private val dbHelper = context?.let { DBHelper(it) } //삭제요망

    //lateinit var sqlDB: SQLiteDatabase //삭제요망

    //필요없는 줄 삭제

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is MainActivity) mainActivity = context
    }

    //수정2
    //lateinit var achvMission: TextView //삭제요망


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_write, container, false)
        //val sharedPreference = requireContext().getSharedPreferences("current", Context.MODE_PRIVATE)
        //val nickname = sharedPreference.getString("nickname", "")
        //dbHelper = context?.let { DBHelper(it) }!!
        val dbManager = DBManager(view.context) //함수들있음
        //sqlDB = dbHelper.writableDatabase  //sql에 테이블 만드는것

        val sharedPreference = view.context.getSharedPreferences("current", Context.MODE_PRIVATE)
        val date = sharedPreference.getString("date", "")
        //수정함2
        val selectButton = view.findViewById<Button>(R.id.write_btnSelect)
        val achvMission = view.findViewById<TextView>(R.id.write_tvAchvMission)
        val write_btnSave = view.findViewById<TextView>(R.id.write_btnSave)

        val write_editDate = view.findViewById<TextView>(R.id.write_editDate)
        val write_editTitle = view.findViewById<TextView>(R.id.write_editTitle)
        val write_editImpre = view.findViewById<TextView>(R.id.write_editImpre)
        val write_ratingBar3 = view.findViewById<RatingBar>(R.id.write_ratingBar3)
        val write_btnCancel = view.findViewById<Button>(R.id.write_btnCancel)
        //
        //원래 이게 맞는데 넣으면 오류나서 앱꺼짐
        //val missionList = dbManager.getChallenges("daily")

        val missionList = dbManager.getTips("tip")


        //var colorArray: Array<String> = arrayOf("치어(어린생선)나 생선알은 먹지 않기","프린트 할 때는 양면 인쇄 하기","환경 관련 뉴스에 관심 기울이며 가족과 친구들에게 환경 뉴스 공유하기")
        //var colorArray: Array<String> = arrayOf(missionList)

        //성취미션선택 버튼 클릭시
        selectButton.setOnClickListener {

            //test를 위해 임의로 성공으로 설정 해놓음
            //dbManager.setIsAchieved(8)
            //dbManager.setIsAchieved(0)
            //dbManager.setIsAchieved(3)
            //dbManager.setIsAchieved(13)
            //dbManager.setIsAchieved(6)

            //미션번호의 인덱스 배열을 가져옴
            val missionList2 = dbManager.getSuccessChallenges(date,'Y')
            //Toast.makeText(context, date, Toast.LENGTH_SHORT).show()

            var size = missionList2.size
            var missionArray: Array<String?> = arrayOfNulls(size)
            for(i in 0 until size) {
                if (missionList2[i] > 24) {
                    missionArray[i] = dbManager.getCustomChallenge(date, missionList2[i])
                } else {
                    missionArray[i] = dbManager.getChallenge(missionList2[i])
                }
            }

            //다이얼로그로 성공한 미션들을 띄어줌
            val builder = AlertDialog.Builder(context)
            builder.setTitle("미션을 선택해주세요").setItems(missionArray, DialogInterface.OnClickListener { dialogInterface, which ->
                achvMission.text = missionArray?.get(which)
            })
            builder.show()
        }

        //소감을 다 기록하고 저장버튼을 클릭했을 때
        write_btnSave.setOnClickListener {

            var title:String = write_editTitle.text.toString()
            var date:String = write_editDate.text.toString()
            var contents:String = write_editImpre.text.toString()
            var score = write_ratingBar3.getRating().toInt()
            var selected_challenge = achvMission.text.toString()

            if (dbManager.getTitle(date) != null) {
                // 해당 날짜에 소감이 존재할 경우
                Toast.makeText(context, "소감 등록이 불가합니다. ${date}의 소감을 이미 등록하셨습니다.", Toast.LENGTH_LONG).show()
                write_editDate.requestFocus()
            } else {
                if( title.length==0 || date.length==0 || contents.length==0){
                    Toast.makeText(context, "빈 칸을 채워주세요.",Toast.LENGTH_SHORT).show()
                }
                else {
                    dbManager.addDiary(date, title, contents, score,selected_challenge)
                    Toast.makeText(context, "소감이 저장되었습니다..",Toast.LENGTH_SHORT).show()
                    mainActivity.goMenu2()
                }
            }
        }

        //취소버튼을 클릭했을 때
        write_btnCancel.setOnClickListener {
            //(activity as MainActivity).goBack()
            (activity as MainActivity).onBackPressed()
        }

        //binding = FragmentWriteBinding.inflate(inflater, container, false)
        //return binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //삭제요망
        /*binding.selectButton.setOnClickListener {
            //mainActivity.goWrite()
        }*/

    }

}