package com.example.bottnav

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.children
//import com.example.bottnav.databinding.FragmentMenu2Binding
import com.example.bottnav.databinding.FragmentWriteBinding

class WriteFragment : Fragment() {

    lateinit var binding: FragmentWriteBinding //수정함
    lateinit var mainActivity: MainActivity

    //추가

    override fun onAttach(context: Context) { //context에는 나를 삽입한 activity가 담겨있음 mainActivity가 맞다면 담아놓고 쓰겠음
        super.onAttach(context)
        if(context is MainActivity) mainActivity = context
    }

    //수정2
    lateinit var achvMission: TextView
    lateinit var myDBHelper: DBHelper
    lateinit var sqlDB: SQLiteDatabase


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_write, container, false)
        //val sharedPreference = requireContext().getSharedPreferences("current", Context.MODE_PRIVATE)
        //val nickname = sharedPreference.getString("nickname", "")

        val dbManager = DBManager(view.context)

        //수정함2
        val selectButton = view.findViewById<Button>(R.id.selectButton)
        val achvMission = view.findViewById<TextView>(R.id.achvMission)

        //
        //원래 이게 맞는데 넣으면 오류나서 앱꺼짐
        //val missionList = dbManager.getChallenges("daily")

        val missionList = dbManager.getTips("tip")

        //var colorArray: Array<String> = arrayOf("빨강","주황","초록")
        //var colorArray: Array<String> = arrayOf(missionList)


        selectButton.setOnClickListener {
            //sqlDB = myDBHelper.readableDatabase
            //var cursor:Cursor
            //cursor = sqlDB.rawQuery("SELECT ")
            val builder = AlertDialog.Builder(context)
            builder.setTitle("미션을 선택해주세요").setItems(missionList,DialogInterface.OnClickListener { dialogInterface, which ->
                achvMission.text = missionList?.get(which)
            })
            //val diarySelectmissionfragment = DiarySelectMissionFragment()
            //achvMission.setText("하이")
            builder.show()
        }

        binding = FragmentWriteBinding.inflate(inflater, container, false)
        //return binding.root
        return view
    }

    //val selectButton = view.findViewById<Button>(R.id.selectButton)
    //val achvMission = view.findViewById<TextView>(R.id.achvMission)
        /*var missionArray: Array<String> = arrayOf("쓰레기 줍기", "계단이용하기")
        selectButton.setOnClickListener{
            achvMission.text = "야호"
            //val builder = AlertDialog.Builder(it)
        }*/
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.selectButton.setOnClickListener {
            //mainActivity.goWrite()
        }

    }

}