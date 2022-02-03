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
import java.time.LocalDate

class Menu1Fragment : Fragment() {

    // lateinit var binding: FragmentMenu1Binding
    lateinit var mainActivity : MainActivity
    lateinit var dbManager: DBManager
    lateinit var addButton : FloatingActionButton

    var todayChallenges = ArrayList<Int>()
    val notyetChallenges = ArrayList<Challenge>()
    val completedChallenges = ArrayList<Challenge>()

    lateinit var date : String


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is MainActivity) mainActivity = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // db에서 미션 배열 가져오기
        val view2 = inflater.inflate(R.layout.fragment_menu1, container, false)
        val todoItem = view2.findViewById<RecyclerView>(R.id.todoItem)
        val completedItem = view2.findViewById<RecyclerView>(R.id.completedItem)

        val sharedPreference = view2.context.getSharedPreferences("current", Context.MODE_PRIVATE)
        date = sharedPreference.getString("date", "").toString()

        addButton = view2.findViewById(R.id.addButton)
        dbManager = DBManager(view2.context)





        val todayChallengeIdx = dbManager.getTodayChallenges(date)
        val todayChallengeNum = ArrayList<Int>()




        // 성공하지 못한 미션만 불러오기?



        val myAdapter2 = CompletedAdapter(view2.context, completedChallenges)

        //어댑터 받아오기
        val myAdapter = TodoListAdapter(view2.context, notyetChallenges){ todo ->


            getMissions()


            // 성공한 미션 불러오기
            completedItem.adapter = myAdapter2

            val lm2 = LinearLayoutManager(view2.context)
            completedItem.layoutManager = lm2
            completedItem.setHasFixedSize(true)

            // (toast 대신 다이얼로그 생성)
            Toast.makeText(getActivity(), "달성됨", Toast.LENGTH_SHORT).show()
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

    data class Challenge(var contents: String, var index: Int)

    public fun getMissions() {
        // 미션 리스트 다시 불러오기

        todayChallenges = dbManager.getTodayChallenges(date)
        completedChallenges.clear()
        notyetChallenges.clear()

        for (i in 0 until todayChallenges.size) {
            if (dbManager.getIsAchieved(date, todayChallenges[i])!! == 'Y') {
                // 달성한 미션일 경우
                when (todayChallenges[i]) {
                    in 0..5 -> {
                        completedChallenges.add(Challenge("[Month] "+dbManager.getChallenge(todayChallenges[i])!!, todayChallenges[i]))
                    }
                    in 6..13 -> {
                        completedChallenges.add(Challenge("[Week] "+dbManager.getChallenge(todayChallenges[i])!!, todayChallenges[i]))
                    }
                    in 14..24 -> {
                        completedChallenges.add(Challenge("[Day] "+dbManager.getChallenge(todayChallenges[i])!!, todayChallenges[i]))
                    }
                    else -> {
                        completedChallenges.add(Challenge("[Day] "+dbManager.getCustomChallenge(date, todayChallenges[i])!!, todayChallenges[i]))
                    }
                }

            } else {
                // 아직 달성하지 않은 미션일 경우
                when (todayChallenges[i]) {
                    in 0..5 -> {
                        notyetChallenges.add(Challenge("[Month] "+dbManager.getChallenge(todayChallenges[i])!!, todayChallenges[i]))
                    }
                    in 6..13 -> {
                        notyetChallenges.add(Challenge("[Week] "+dbManager.getChallenge(todayChallenges[i])!!, todayChallenges[i]))
                    }
                    in 14..24 -> {
                        notyetChallenges.add(Challenge("[Day] "+dbManager.getChallenge(todayChallenges[i])!!, todayChallenges[i]))
                    }
                    else -> {
                        notyetChallenges.add(Challenge("[Day] "+dbManager.getCustomChallenge(date, todayChallenges[i])!!, todayChallenges[i]))
                    }
                }
            }
        }
    }

}

