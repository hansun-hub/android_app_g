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

class TodoListAdapter(val context: Context, val todoList: ArrayList<String>) :
    RecyclerView.Adapter<TodoListAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.fragment_todo_item, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return todoList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder?.bind(todoList[position], context)
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

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

                private fun deleteToDo(TODO: String) {}
            })

            fun setItem(item: Note) {
                checkBox.text = item.getTodo()
            }

            fun setLayout() {
                layoutTodo.visibility = View.VISIBLE
            }
        }



        val todoCheck = itemView?.findViewById<CheckBox>(R.id.checkBox)

        fun bind (todo: String, context: Context) {
            todoCheck?.text = todo
        }
    }
}