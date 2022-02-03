package com.example.bottnav

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CompletedAdapter(val context: Context, val completedList: ArrayList<Menu1Fragment.Challenge>) :
    RecyclerView.Adapter<CompletedAdapter.Holder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.fragment_completed_item, parent, false)

        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder?.bind(completedList[position], context)
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView){

        // 레이아웃에 string이 text로 나올 수 있도록 표현
        val menu1_tvCompletedWork : TextView = itemView?.findViewById(R.id.menu1_tvCompletedWork)
        fun bind (todo: Menu1Fragment.Challenge, context: Context) {
            menu1_tvCompletedWork?.setText(todo.contents)
        }
    }


    override fun getItemCount(): Int {
        return completedList.size
    }



}