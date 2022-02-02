package com.example.bottnav

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bottnav.databinding.FragmentMenu1Binding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class Menu1Fragment : Fragment() {

    lateinit var binding: FragmentMenu1Binding
    lateinit var mainActivity : MainActivity
    lateinit var dbManager: DBManager
    lateinit var todoItem : RecyclerView
    lateinit var addButton : FloatingActionButton

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is MainActivity) mainActivity = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //db에서 미션 배열 가져오기(아직 DB와 연결 안됨)
        // Toast.makeText(getActivity(), "$random", Toast.LENGTH_SHORT).show()

        val view2 = inflater.inflate(R.layout.fragment_menu1, container, false)
        val todoItem = view2.findViewById<RecyclerView>(R.id.todoItem)

        addButton = view2.findViewById(R.id.addButton)
        dbManager = DBManager(view2.context)




        // val todayChallenges = dbManager.getTodayChallenges("")



        // 임의로 지정한 arraylist
        var todayChallenges = arrayListOf<String>(
            "Chow Chow, Male, 4, dog00",
            "Breed Pomeranian, Female, 1, dog01",
            "Golden Retriver, Female, 3, dog02"
        )

        //어댑터 받아오기
        val myAdapter = TodoListAdapter(view2.context, todayChallenges){ String ->
            Toast.makeText(getActivity(), "삭제됨", Toast.LENGTH_SHORT).show()
        }
        todoItem.adapter = myAdapter

        val lm = LinearLayoutManager(view2.context)
        todoItem.layoutManager = lm
        todoItem.setHasFixedSize(true)


        addButton.setOnClickListener {
            mainActivity.goEditTodo()
        }

        return view2
    }


}