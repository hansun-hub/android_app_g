package com.example.bottnav

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bottnav.databinding.FragmentMenu1Binding

class Menu1Fragment : Fragment() {

    lateinit var binding: FragmentMenu1Binding
    lateinit var mainActivity : MainActivity
    lateinit var dbManager: DBManager
    lateinit var todoItem : RecyclerView

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is MainActivity) mainActivity = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //db에서 미션 배열 가져옴
        // Toast.makeText(getActivity(), "$random", Toast.LENGTH_SHORT).show()

        val view = inflater.inflate(R.layout.fragment_todo_item, container, false)
        val todoItem = view.findViewById<RecyclerView>(R.id.todoItem)

        dbManager = DBManager(view.context)
        // val todayChallenges = dbManager.getTodayChallenges("")

        /*
        var challengeSize = (todayChallenges!!.size).toInt()
        val random = (0..challengeSize-1).random()
        todoItem.adapter = todayChallenges?.get(random)
         */

        // todoItem.layoutManager(LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL ,false))


        var todayChallenges = arrayListOf<String>(
            "Chow Chow, Male, 4, dog00",
            "Breed Pomeranian, Female, 1, dog01",
            "Golden Retriver, Female, 3, dog02"
        )


        val myAdapter = TodoListAdapter(view.context, todayChallenges)
        todoItem.adapter = myAdapter

        val lm = LinearLayoutManager(view.context)
        todoItem.layoutManager = lm
        todoItem.setHasFixedSize(true)


        //binding = FragmentMenu1Binding.inflate(inflater, container, false)
        //return binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addButton.setOnClickListener {
            mainActivity.goEditTodo()
        }
    }

}