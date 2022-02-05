package com.example.bottnav

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CompletedAdapter(val context: Context, val completedList: ArrayList<Menu1Fragment.Challenge>) :
    RecyclerView.Adapter<CompletedAdapter.Holder>(){

    // xml파일을 inflate하여 ViewHolder를 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.fragment_completed_item, parent, false)

        return Holder(view)
    }

    // view와 입력되는 데이터 연결
    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder?.bind(completedList[position], context)
    }

    // 아이템 뷰 저장
    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView){

        // textView 레이아웃에 미션 연결
        val menu1_tvCompletedWork : TextView = itemView?.findViewById(R.id.menu1_tvCompletedWork)
        fun bind (todo: Menu1Fragment.Challenge, context: Context) {
            menu1_tvCompletedWork?.setText(todo.contents)
        }
    }

    // item 총 개수 반환
    override fun getItemCount(): Int {
        return completedList.size
    }



}