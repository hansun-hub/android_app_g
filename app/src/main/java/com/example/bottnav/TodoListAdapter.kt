package com.example.bottnav

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class TodoListAdapter(val context: Context, val todoList: ArrayList<String>, val itemClick: (String) -> Unit) :
    RecyclerView.Adapter<TodoListAdapter.Holder>() {

    // 화면을 최초 로딩하여 만들어진 View가 없는 경우, xml파일을 inflate하여 ViewHolder를 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.fragment_todo_item, parent, false)
        return Holder(view, itemClick)
    }

    // RecyclerView로 만들어지는 item의 총 개수를 반환
    override fun getItemCount(): Int {
        return todoList.size
    }

    // onCreateViewHolder에서 만든 view와 실제 입력되는 각각의 데이터를 연결
    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder?.bind(todoList[position], context)
    }

    inner class Holder(itemView: View, itemClick: (String) -> Unit) : RecyclerView.ViewHolder(itemView) {

        val layoutTodo : LinearLayout = itemView.findViewById<LinearLayout?>(R.id.layoutTodo)
        val checkBox : CheckBox = itemView.findViewById<android.widget.CheckBox?>(R.id.checkBox)
        val deleteButton : Button = itemView.findViewById<android.widget.Button?>(R.id.deleteButton)

        // 체크박스 창 구현
        init {
            deleteButton.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View) {

                    val TODO = checkBox.getText() as String
                    deleteToDo(TODO)
                    Toast.makeText(v.context, "삭제되었습니다.", Toast.LENGTH_SHORT).show()
                }

                // (삭제 버튼 아직 구현 안됨)
                private fun deleteToDo(TODO: String) {}
            })
        }


        val todoCheck = itemView?.findViewById<CheckBox>(R.id.checkBox)
        // 체크박스에 string이 text로 나올 수 있도록 표현
        fun bind (todo: String, context: Context) {
            todoCheck?.setText(todo)

            todoCheck.setOnClickListener{ itemClick(todo)}
        }


    }
}