package com.example.bottnav

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CalendarView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class Menu1Fragment : Fragment() {

    // lateinit var binding: FragmentMenu1Binding
    lateinit var mainActivity : MainActivity
    lateinit var dbManager: DBManager
    lateinit var btnAdd : Button
    lateinit var calendar: CalendarView
    lateinit var calendarDate: String

    var todayChallenges = ArrayList<Int>()
    var notyetChallenges = ArrayList<Challenge>()
    var completedChallenges = ArrayList<Challenge>()

    lateinit var recyclerTodo: RecyclerView
    lateinit var recyclerComp: RecyclerView
    lateinit var recyclerTodo_other: RecyclerView
    lateinit var recyclerComp_other: RecyclerView

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
        val view = inflater.inflate(R.layout.fragment_menu1, container, false)
        val sharedPreference = view.context.getSharedPreferences("current", Context.MODE_PRIVATE)
        date = sharedPreference.getString("date", "").toString()
        calendarDate = date

        dbManager = DBManager(view.context)

        // 리사이클러 뷰 연결
        recyclerTodo = view.findViewById<RecyclerView>(R.id.menu1_recyclerTodo)
        recyclerTodo.layoutManager = LinearLayoutManager(view.context)

        recyclerComp = view.findViewById<RecyclerView>(R.id.menu1_recyclerComp)
        recyclerComp.layoutManager = LinearLayoutManager(view.context)


        recyclerTodo_other = view.findViewById<RecyclerView>(R.id.menu1_recyclerTodo_other)
        recyclerTodo_other.layoutManager = LinearLayoutManager(view.context)

        recyclerComp_other = view.findViewById<RecyclerView>(R.id.menu1_recyclerComp_other)
        recyclerComp_other.layoutManager = LinearLayoutManager(view.context)



        calendar = view.findViewById(R.id.calendarView)
        btnAdd = view.findViewById(R.id.menu1_btnAdd)

        // 미션 불러오기
        notyetChallenges = missionsPerDay(date).uncompleted
        completedChallenges = missionsPerDay(date).completed

        // adapter 연결
        val adapter1 = TodoListAdapter(view.context, notyetChallenges)
        recyclerTodo.adapter = adapter1

        val adapter2 = CompletedAdapter(view.context, completedChallenges)
        recyclerComp.adapter = adapter2

        adapter1.setOnItemClickListener(object: TodoListAdapter.OnItemClickListener {
            override fun onItemClick(v: View, todo: Challenge, position: Int) {
                // 달성
                recyclerTodo.adapter = adapter1
                recyclerComp.adapter = adapter2

                // DB 수정
                dbManager.setIsAchieved(todo.index)
                dbManager.setLevel(dbManager.getLevel())

                // 배열 수정
                completedChallenges.add(0, notyetChallenges[position])
                notyetChallenges.remove(notyetChallenges[position])

                adapter1.notifyDataSetChanged()
                adapter2.notifyDataSetChanged()
            }

            override fun onItemDeleteClick(v: View, todo: Challenge, position: Int) {
                // 삭제
                recyclerTodo.adapter = adapter1
                recyclerComp.adapter = adapter2

                if (todo.index > 24) {
                    // DB 수젇
                    dbManager.delCustomChallenge(date, todo.index)

                    // 배열 수정
                    notyetChallenges.remove(notyetChallenges[position])

                    Toast.makeText(view.context, "삭제했습니다.", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(view.context, "삭제가 불가능한 미션입니다.", Toast.LENGTH_SHORT).show()
                }

                adapter1.notifyDataSetChanged()
            }
        })

        recyclerTodo.setHasFixedSize(true)

        // calendar에서 날짜 선택 시 이벤트 발생
        calendar.setOnDateChangeListener { calendarView, year, month, day ->
            var monthStr = if (month+1 < 10) "0${month+1}" else (month+1).toString()
            var datStr = if (day < 10) "0$day" else day.toString()

            calendarDate = "$year-${monthStr}-$datStr"
            if (calendarDate != date) {
                // 오늘 날짜가 아닌 다른 날짜 선택 시
                recyclerComp.visibility = View.GONE
                recyclerTodo.visibility = View.GONE
                recyclerComp_other.visibility = View.VISIBLE
                recyclerTodo_other.visibility = View.VISIBLE

                // 오늘이 아닌 날짜의 미션

                val adapter3 = TodoListAdapter(view.context, missionsPerDay(calendarDate).uncompleted)
                recyclerTodo_other.adapter = adapter3

                val adapter4 = CompletedAdapter(view.context, missionsPerDay(calendarDate).completed)
                recyclerComp_other.adapter = adapter4

                adapter3.setOnItemClickListener(object: TodoListAdapter.OnItemClickListener {
                    override fun onItemClick(v: View, todo: Challenge, position: Int) {
                        Toast.makeText(view.context, "접근 불가한 미션입니다.", Toast.LENGTH_SHORT).show()
                    }

                    override fun onItemDeleteClick(v: View, todo: Challenge, position: Int) {
                    }
                })
            } else {
                // 오늘 날짜 선택 시
                recyclerComp.visibility = View.VISIBLE
                recyclerTodo.visibility = View.VISIBLE
                recyclerComp_other.visibility = View.GONE
                recyclerTodo_other.visibility = View.GONE
            }
        }


        btnAdd.setOnClickListener {
            // 사용자 생성 미션
            mainActivity.goEditTodo()
        }

        return view
    }

    data class Challenge(
        var contents: String,
        var index: Int,
        var isToday: Boolean
    )

    inner class missionsPerDay(findDate: String?) {
        // 각 날짜별 미션 현황 클래스

        val todayChallenges = dbManager.getTodayChallenges(findDate)
        val completed = ArrayList<Challenge>()
        val uncompleted = ArrayList<Challenge>()
        val isToday = findDate == date

        init {
            for (i in 0 until todayChallenges.size) {
                if (dbManager.getIsAchieved(findDate!!, todayChallenges[i])!! == 'Y') {
                    // 달성한 미션일 경우
                    when (todayChallenges[i]) {
                        in 0..5 -> {
                            completed.add(Challenge("[Month] "+dbManager.getChallenge(todayChallenges[i])!!, todayChallenges[i], isToday))
                        }
                        in 6..13 -> {
                            completed.add(Challenge("[Week] "+dbManager.getChallenge(todayChallenges[i])!!, todayChallenges[i], isToday))
                        }
                        in 14..24 -> {
                            completed.add(Challenge("[Day] "+dbManager.getChallenge(todayChallenges[i])!!, todayChallenges[i], isToday))
                        }
                        else -> {
                            completed.add(Challenge("[Day] "+dbManager.getCustomChallenge(date, todayChallenges[i])!!, todayChallenges[i], isToday))
                        }
                    }

                } else {
                    // 아직 달성하지 않은 미션일 경우
                    when (todayChallenges[i]) {
                        in 0..5 -> {
                            uncompleted.add(Challenge("[Month] "+dbManager.getChallenge(todayChallenges[i])!!, todayChallenges[i], isToday))
                        }
                        in 6..13 -> {
                            uncompleted.add(Challenge("[Week] "+dbManager.getChallenge(todayChallenges[i])!!, todayChallenges[i], isToday))
                        }
                        in 14..24 -> {
                            uncompleted.add(Challenge("[Day] "+dbManager.getChallenge(todayChallenges[i])!!, todayChallenges[i], isToday))
                        }
                        else -> {
                            uncompleted.add(Challenge("[Day] "+dbManager.getCustomChallenge(date, todayChallenges[i])!!, todayChallenges[i], isToday))
                        }
                    }
                }
            }
        }
    }
}