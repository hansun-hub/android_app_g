package com.example.bottnav

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView

class Menu2DiaryAdapter(
    val context: Context,
    private val recordList: ArrayList<Menu2Fragment.DiaryRecord>
) : RecyclerView.Adapter<Menu2DiaryAdapter.CustomViewHolder>() {
    // menu2 기록 - 지난 기록 리사이클러 뷰 어댑터

    val dbManager = DBManager(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            CustomViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_menu2_list_item, parent, false)
        return CustomViewHolder(view).apply {
            //리사이클러뷰에서 아이템 클릭구현
            itemView.setOnClickListener {
                //curPos로 몇 번째 클릭했는지 번호를 받음
                val curPos: Int = absoluteAdapterPosition
                val diaryRecord = recordList[curPos]

                //intent로 화면전환할 때 putExtra로 정보를 넘겨줌
                val intent = Intent(itemView.context, Menu2DetailActivity::class.java)

                intent.putExtra("title", diaryRecord.title)
                intent.putExtra("date", diaryRecord.date)
                intent.putExtra("contents", diaryRecord.contents)
                intent.putExtra("selectedChallenge", diaryRecord.selectedChallenge)
                intent.putExtra("rate", diaryRecord.rate)

                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.title.text = recordList[position].title
        holder.date.text = recordList[position].date
    }

    override fun getItemCount(): Int {
        return recordList.size
    }

    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.tv_title)
        val date: TextView = itemView.findViewById(R.id.tv_date)
    }
}
