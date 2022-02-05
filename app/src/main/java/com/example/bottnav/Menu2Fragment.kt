package com.example.bottnav

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

//하단 메뉴 중 기록메뉴
class Menu2Fragment : Fragment() {

    lateinit var mainActivity: MainActivity
    lateinit var dbManager: DBManager
    lateinit var adapter: menu2_DiaryAdapter
    val recordList = ArrayList<diaryRecord>()


    override fun onAttach(context: Context) {
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
        val menu2_btnAdd = view.findViewById<Button>(R.id.menu2_btnAdd)
        val menu2WarningText = view.findViewById<TextView>(R.id.menu2WarningText)

        //소감이 작성된 날짜들 배열가져오기
        val feelingDate = dbManager.getDatesFromDiary()
        var size = feelingDate?.size

        //recordList에 소감 추가
        if (size != null) {
            for (i in 0..size-1!!)
                recordList.add(diaryRecord(feelingDate!![i]))
        }

        //리사이클러뷰를 레이아웃과 연결
        val menu2_recycler = view.findViewById<RecyclerView>(R.id.menu2_recycler)
        menu2_recycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        menu2_recycler.setHasFixedSize(true)
        adapter = menu2_DiaryAdapter(view.context, recordList)
        menu2_recycler.adapter = adapter

        //경고메시지 가져오기
        val missionList = dbManager.getTips("warn")
        var tipSize = (missionList!!.size).toInt()

        //경고메시지를 랜덤으로 menu2WarningText에 넣어주기
        val random = (0..tipSize-1).random()
        menu2WarningText.text = missionList?.get(random)

        //추가 버튼 클릭시 writeFragment로 화면 전환
        menu2_btnAdd.setOnClickListener{
            val writeFragment = WriteFragment()
            mainActivity.replaceFragmentExit(writeFragment)
        }
        return view
    }

    //기록 삭제시 onResume()을 통해 리사이클러뷰에 업데이트해줌
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

    //diary에 기록될 요소명시
    inner class diaryRecord(findDate: String?) {
        val date = findDate
        val title = dbManager.getTitle(findDate!!)
        val contents = dbManager.getContents(findDate!!)
        val selectedChallenge = dbManager.getSelectedChallenge(findDate!!, title)
        val rate = dbManager.getRate(findDate!!)
    }
}