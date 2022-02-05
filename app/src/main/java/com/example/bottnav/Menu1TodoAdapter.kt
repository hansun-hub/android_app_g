package com.example.bottnav

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView

class Menu1TodoAdapter(context: Context, list: ArrayList<Menu1Fragment.Challenge>) :
    RecyclerView.Adapter<Menu1TodoAdapter.ViewHolder>() {

    val dbManager: DBManager = DBManager(context)
    private var todoList: ArrayList<Menu1Fragment.Challenge>? = list
    private var listener: OnItemClickListener? = null

    // 커스텀 리스너 인터페이스 정의
    interface OnItemClickListener {
        fun onItemClick(v: View, todo: Menu1Fragment.Challenge, position: Int)
        fun onItemDeleteClick(v: View, todo: Menu1Fragment.Challenge, position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }


    // 화면을 최초 로딩하여 만들어진 View가 없는 경우, xml파일을 inflate하여 ViewHolder를 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context: Context = parent.context
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = inflater.inflate(R.layout.layout_menu1_todo_item, parent, false)

        return ViewHolder(view)
    }

    // RecyclerView로 만들어지는 item의 총 개수를 반환
    override fun getItemCount(): Int {
        return todoList!!.size
    }

    // onCreateViewHolder에서 만든 view와 실제 입력되는 각각의 데이터를 연결
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = todoList!![position]

        holder.checkBox.isEnabled = item.isToday

        holder.checkBox.setOnClickListener {
            listener?.onItemClick(it, item, position)
        }
        holder.deleteButton.setOnClickListener {
            listener?.onItemDeleteClick(it, item, position)
        }

        holder.checkBox.text = todoList!![position].contents
        holder?.bind(todoList!![position])
        holder.setIsRecyclable(false)
    }

    // 아이템 뷰 저장
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val layoutTodo: LinearLayout = itemView.findViewById<LinearLayout?>(R.id.layoutTodo)
        val checkBox: CheckBox = itemView.findViewById<android.widget.CheckBox?>(R.id.checkBox)
        val deleteButton: Button = itemView.findViewById<android.widget.Button?>(R.id.deleteButton)

        // 체크박스 레이아웃에 string이 text로 나올 수 있도록 표현
        fun bind(todo: Menu1Fragment.Challenge) {
            checkBox.text = todo.contents

            val pos = absoluteAdapterPosition
            if (pos != RecyclerView.NO_POSITION) {
                itemView.setOnClickListener {
                    listener?.onItemClick(itemView, todo, pos)
                }
            }
        }
    }
}