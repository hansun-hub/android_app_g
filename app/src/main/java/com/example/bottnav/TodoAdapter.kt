package com.example.bottnav

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
/*
class TodoAdapter(context: Context) : RecyclerView.Adapter<TodoAdapter.ViewHolder>(){

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

        val layoutTodo : LinearLayout = itemView.findViewById<LinearLayout?>(R.id.layoutTodo)
        val checkBox : CheckBox = itemView.findViewById<android.widget.CheckBox?>(R.id.checkBox)
        val deleteButton : Button = itemView.findViewById<android.widget.Button?>(R.id.deleteButton)

        init {
            deleteButton.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View) {

                    val TODO = checkBox.getText() as String
                    deleteToDo(TODO)
                    Toast.makeText(v.context, "삭제되었습니다.", Toast.LENGTH_SHORT).show()
                }

                private fun deleteToDo(TODO: String) {

                }
            })

            fun setItem(item: Note) {
                checkBox.text = item.getTodo()
            }

            fun setLayout() {
                layoutTodo.visibility = View.VISIBLE
            }
        }

    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoAdapter.ViewHolder {

        // 아이템 창 띄우기
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.fragment_todo_item, parent, false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TodoAdapter.ViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 0
    }
}

 */