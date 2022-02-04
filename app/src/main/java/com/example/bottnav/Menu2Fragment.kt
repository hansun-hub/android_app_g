package com.example.bottnav

import android.annotation.SuppressLint
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Menu2Fragment : Fragment() {

    lateinit var mainActivity: MainActivity
    lateinit var menu2DetailLayout: LinearLayout
    lateinit var dbManager: DBManager
    lateinit var adapter: menu2_DiaryAdapter
    val recordList = ArrayList<diaryRecord>()

    //추가
    override fun onAttach(context: Context) { //context에는 나를 삽입한 activity가 담겨있음 mainActivity가 맞다면 담아놓고 쓰겠음
        super.onAttach(context)
        if(context is MainActivity) mainActivity = context
    }

    @SuppressLint("ResourceType")
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_menu2, container, false)
        dbManager = DBManager(view.context)
        val button4 = view.findViewById<Button>(R.id.button4)
        val menu2WarningText = view.findViewById<TextView>(R.id.menu2WarningText)
        //val menu2DetailLayout: LinearLayout = view.findViewById(R.id.menu2_DetailLayout)
        //val newButton = TextView(context)

        //소감이 작성된 날짜배열
        val feelingDate = dbManager.getDatesFromDiary()
        var size = feelingDate?.size

        if (size != null) {
            // recordList에 소감 추가
            for (i in 0..size-1!!)
                recordList.add(diaryRecord(feelingDate!![i]))
        }

        val menu2_recycler = view.findViewById<RecyclerView>(R.id.menu2_recycler)
        menu2_recycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        menu2_recycler.setHasFixedSize(true)
        adapter = menu2_DiaryAdapter(view.context, recordList)
        menu2_recycler.adapter = adapter

        val missionList = dbManager.getTips("warn")

        var tipSize = (missionList!!.size).toInt()

        val random = (0..tipSize-1).random()
        menu2WarningText.text = missionList?.get(random)


        button4.setOnClickListener{
            //화면 전환 (writeFragment로)
            val writeFragment = WriteFragment()
            (activity as MainActivity).replaceFragment(writeFragment)
        }
        return view


    }
    override fun onResume() {
        super.onResume()
        val feelingDate = dbManager.getDatesFromDiary()
        var size = feelingDate?.size

        if (size != null) {
            recordList.clear()

            // recordList에 소감 추가
            for (i in 0..size-1!!)
                recordList.add(diaryRecord(feelingDate!![i]))
        }

        adapter.notifyDataSetChanged()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    inner class diaryRecord(findDate: String?) {
        val date = findDate
        val title = dbManager.getTitle(findDate!!)
        val contents = dbManager.getContents(findDate!!)
        val selectedChallenge = dbManager.getSelectedChallenge(findDate!!, title)
        val rate = dbManager.getRate(findDate!!)
    }




}