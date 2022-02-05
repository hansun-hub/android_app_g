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
import android.widget.*
import androidx.annotation.RequiresApi

class WriteFragment : Fragment() {

    lateinit var mainActivity: MainActivity
    lateinit var dbHelper: DBHelper //삭제요망
  
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is MainActivity) mainActivity = context
    }

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
        val write_btnSave = view.findViewById<Button>(R.id.write_btnSave)

        val write_editDate = view.findViewById<EditText>(R.id.write_editDate)
        val write_editTitle = view.findViewById<EditText>(R.id.write_editTitle)
        val write_editImpre = view.findViewById<EditText>(R.id.write_editImpre)
        val write_ratingBar3 = view.findViewById<RatingBar>(R.id.write_ratingBar3)
        val write_btnCancel = view.findViewById<Button>(R.id.write_btnCancel)
        //
        //원래 이게 맞는데 넣으면 오류나서 앱꺼짐
        //val missionList = dbManager.getChallenges("daily")
        //val missionList = dbManager.getTips("tip")
        //var colorArray: Array<String> = arrayOf("치어(어린생선)나 생선알은 먹지 않기","프린트 할 때는 양면 인쇄 하기","환경 관련 뉴스에 관심 기울이며 가족과 친구들에게 환경 뉴스 공유하기")
        //var colorArray: Array<String> = arrayOf(missionList)

        //성취미션선택 버튼 클릭시
        selectButton.setOnClickListener {
          
            val sellectDate  = write_editDate.text.toString()

            //Toast.makeText(context,sellectDate,Toast.LENGTH_SHORT).show()
            //미션번호의 인덱스 배열을 가져옴
            val missionList2 = dbManager.getSuccessChallenges(sellectDate,'Y')
            //Toast.makeText(context, date, Toast.LENGTH_SHORT).show()

            var size = missionList2.size
            if(size>0){

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
            else{
                val builder = AlertDialog.Builder(context)
                builder.setTitle(" 성취한 미션이 없습니다. \n 일자를 다시 확인해주세요.\n").setNegativeButton("확인", DialogInterface.OnClickListener { dialog, id ->

                })
                builder.show()
            }

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
                    val menu2Fragment = Menu2Fragment()
                    mainActivity.replaceFragmentExit(menu2Fragment)

                }
            }
        }

        //취소버튼을 클릭했을 때
        write_btnCancel.setOnClickListener {
            (activity as MainActivity).onBackPressed()
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}