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

class Menu2WriteFragment : Fragment() {
    // menu2 기록 - 새로운 소감 작성 fragment

    lateinit var mainActivity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainActivity) mainActivity = context
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_menu2_write, container, false)
        val dbManager = DBManager(view.context)
        val sharedPreference = view.context.getSharedPreferences("current", Context.MODE_PRIVATE)
        val date = sharedPreference.getString("date", "")

        val selectButton = view.findViewById<Button>(R.id.write_btnSelect)
        val achvMission = view.findViewById<TextView>(R.id.write_tvAchvMission)
        val write_btnSave = view.findViewById<Button>(R.id.write_btnSave)
        val write_editDate = view.findViewById<EditText>(R.id.write_editDate)
        val write_editTitle = view.findViewById<EditText>(R.id.write_editTitle)
        val write_editImpre = view.findViewById<EditText>(R.id.write_editImpre)
        val write_ratingBar3 = view.findViewById<RatingBar>(R.id.write_ratingBar3)
        val write_btnCancel = view.findViewById<Button>(R.id.write_btnCancel)

        //성취미션선택 버튼 클릭시
        selectButton.setOnClickListener {

            val selectDate = write_editDate.text.toString()

            //미션번호의 인덱스 배열을 가져옴
            val missionList2 = dbManager.getSuccessChallenges(selectDate, 'Y')

            val size = missionList2.size
            if (size > 0) {

                val missionArray: Array<String?> = arrayOfNulls(size)
                for (i in 0 until size) {
                    if (missionList2[i] > 24) {
                        missionArray[i] = dbManager.getCustomChallenge(date, missionList2[i])
                    } else {
                        missionArray[i] = dbManager.getChallenge(missionList2[i])
                    }
                }

                //다이얼로그로 성공한 미션들을 띄어줌
                val builder = AlertDialog.Builder(context)
                builder.setTitle(getString(R.string.menu2_select_challenge)).setItems(
                    missionArray
                ) { dialogInterface, which ->
                    achvMission.text = missionArray[which]
                }
                builder.show()
            } else {
                val builder = AlertDialog.Builder(context)
                builder.setTitle(getString(R.string.menu2_no_completed))
                    .setNegativeButton(getString(R.string.answer_ok)) { dialog, id ->

                    }
                builder.show()
            }
        }

        //소감을 다 기록하고 저장버튼을 클릭했을 때
        write_btnSave.setOnClickListener {

            val title: String = write_editTitle.text.toString()
            val date: String = write_editDate.text.toString()
            val contents: String = write_editImpre.text.toString()
            val score = write_ratingBar3.rating.toInt()
            val selected_challenge = achvMission.text.toString()

            if (dbManager.getTitle(date) != null) {
                // 해당 날짜에 소감이 존재할 경우
                Toast.makeText(context, "소감 등록이 불가합니다. ${date}의 소감을 이미 등록하셨습니다.", Toast.LENGTH_LONG)
                    .show()
                write_editDate.requestFocus()
            } else {
                if (title.isEmpty() || date.isEmpty() || contents.isEmpty()) {
                    Toast.makeText(context, "빈 칸을 채워주세요.", Toast.LENGTH_SHORT).show()
                } else {
                    dbManager.addDiary(date, title, contents, score, selected_challenge)
                    Toast.makeText(context, "소감이 저장되었습니다..", Toast.LENGTH_SHORT).show()
                    val menu2Fragment = Menu2Fragment()
                    mainActivity.replaceFragmentExit(menu2Fragment)

                }
            }
        }

        //취소버튼을 클릭했을 때
        write_btnCancel.setOnClickListener {
            //안내
            Toast.makeText(view.context, getString(R.string.cancel_message), Toast.LENGTH_SHORT)
                .show()

            val menu2Fragment = Menu2Fragment()
            (activity as MainActivity).replaceFragmentExit(menu2Fragment)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}