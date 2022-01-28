package com.example.bottnav

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.TextView
import android.widget.Toast
import com.example.bottnav.databinding.FragmentMenu2Binding
import com.example.bottnav.databinding.FragmentWriteBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.w3c.dom.Text

class Menu2Fragment : Fragment() {

    lateinit var binding: FragmentMenu2Binding //수정함
    lateinit var mainActivity: MainActivity
    lateinit var dbManager : DBManager

    //추가
    override fun onAttach(context: Context) { //context에는 나를 삽입한 activity가 담겨있음 mainActivity가 맞다면 담아놓고 쓰겠음
        super.onAttach(context)
        if(context is MainActivity) mainActivity = context
    }

    //변수 추가함
    lateinit var addButton: FloatingActionButton

    //추가
    lateinit var menu2WarningText: TextView


    @SuppressLint("ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //추가함'
        val view = inflater.inflate(R.layout.fragment_menu2, container, false)
        val menu2WarningText = view.findViewById<TextView>(R.id.menu2WarningText)

        dbManager = DBManager(view.context)
        val missionList = dbManager.getTips("warn")
        //menu2WarningText.setText("안녕")
        //Toast.makeText(getActivity(), "${missionList?.get(2)}", Toast.LENGTH_SHORT).show()

        var tipSize = (missionList!!.size).toInt()

        val random = (0..tipSize-1).random()
        menu2WarningText.text = missionList?.get(random)
        //val strArr = Array(5,{item->""})
        //strArr[0] = "먹이사슬 최상위 포식자로써 북극 생태계 균형을 유지하는 데 중요한 역할을 하는 북극곰은 2050년에 완전히 사라질 지 모릅니다."
        //warningText.setText(strArr[0])
        //warningText.setText("안녕하세요")

        //return view
        //추가
        //val warn = ArrayList<String>()

        //warn.add(R.array.WARNINGS.toString())
        //warningText.setText(warn.get(0).toString())

        //warningText.resources.getString(R.array.TIPS)

        //Toast.makeText(getActivity(), "${missionList?.get(2)}", Toast.LENGTH_SHORT).show()

        binding = FragmentMenu2Binding.inflate(inflater, container, false)
        //return binding.root
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addButton3.setOnClickListener{
            mainActivity.goWrite()
        }
    }


}