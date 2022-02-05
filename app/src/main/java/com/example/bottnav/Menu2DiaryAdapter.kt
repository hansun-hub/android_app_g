package com.example.bottnav

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView

//리사이클러뷰 구현위해 필요한 Adapter
class Menu2DiaryAdapter(
    val context: Context,
    val recordList: ArrayList<Menu2Fragment.diaryRecord>
) : RecyclerView.Adapter<Menu2DiaryAdapter.CustomViewHolder>() {
    val dbManager = DBManager(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            Menu2DiaryAdapter.CustomViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.layout_menu2_list_item, parent, false)
        return CustomViewHolder(view).apply {
            //리사이클러뷰에서 아이템 클릭구현
            itemView.setOnClickListener {
                //curPos로 몇 번째 클릭했는지 번호를 받음
                val curPos: Int = absoluteAdapterPosition
                val DiaryRecord = recordList.get(curPos)

                //intent로 화면전환할 때 putExtra로 정보를 넘겨줌
                val intent = Intent(itemView.context, Menu2DetailActivity::class.java)

                intent.putExtra("title", DiaryRecord.title)
                intent.putExtra("date", DiaryRecord.date)
                intent.putExtra("contents", DiaryRecord.contents)
                intent.putExtra("selectedChallenge", DiaryRecord.selectedChallenge)
                intent.putExtra("rate", DiaryRecord.rate)

                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onBindViewHolder(holder: Menu2DiaryAdapter.CustomViewHolder, position: Int) {
        holder.title.text = recordList.get(position).title
        holder.date.text = recordList.get(position).date
    }

    override fun getItemCount(): Int {
        return recordList.size
    }

    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title = itemView.findViewById<TextView>(R.id.tv_title)
        val date = itemView.findViewById<TextView>(R.id.tv_date)
    }
}
