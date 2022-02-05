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
class menu2_DiaryAdapter(val context: Context,val recordList: ArrayList<Menu2Fragment.diaryRecord>): RecyclerView.Adapter<menu2_DiaryAdapter.CustomViewHolder>()
{
    //데이터를 저장할 아이템리스트 수정함 (버튼클릭)
    //val items = ArrayList<menu2_DiaryRecord>()//삭제요망
    //private var activity:MainActivity? = null//삭제요망
    val dbManager = DBManager(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            menu2_DiaryAdapter.CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.menu2_list_item,parent,false)
        return CustomViewHolder(view).apply {
            //리사이클러뷰에서 아이템 클릭구현
            itemView.setOnClickListener {
                /* 삭제요망
                //화면 전환 (writeFragment로)
                //val menu2_diaryDetailFragment = menu2_diaryDetailFragment()
                //val btnTrash= itemView.findViewById<Button>(R.id.btnTrash)
                 */
                //curPos로 몇 번째 클릭했는지 번호를 받음
                val curPos: Int = absoluteAdapterPosition
                val DiaryRecord = recordList.get(curPos)

                //intent로 화면전환할 때 putExtra로 정보를 넘겨줌
                val intent = Intent(itemView.context, menu2_DetailActivity::class.java)
                //Toast.makeText(parent.context,"제목 : ${DiaryRecord.title}\n 일자 : ${DiaryRecord.date}\n 선택된 미션 : ${DiaryRecord.selectedChallenge}\n 별점 : ${DiaryRecord.rate}",Toast.LENGTH_SHORT).show()
                //삭제요망
                intent.putExtra("title",DiaryRecord.title)
                intent.putExtra("date",DiaryRecord.date)
                intent.putExtra("contents",DiaryRecord.contents)
                intent.putExtra("selectedChallenge",DiaryRecord.selectedChallenge)
                intent.putExtra("rate",DiaryRecord.rate)
                //뒤에 다 삭제요망
                //intent.putExtra("curpos",curPos)
                //notifyItemRemoved(curPos)
                //remove(curPos)
                //val btnTrash= itemView.findViewById<Button>(R.id.btnTrash)
                /*
                btnTrash.setOnClickListener {

                    Toast.makeText(parent.context,"잘 뜬다.${DiaryRecord.title}" ,Toast.LENGTH_SHORT).show()
                    removeItem(curPos)
                    dbManager.delDiary(DiaryRecord.date)
                }*/
                itemView.context.startActivity(intent)
            }
            
        }
    }

    override fun onBindViewHolder(holder: menu2_DiaryAdapter.CustomViewHolder, position: Int) {
        holder.title.text = recordList.get(position).title
        holder.date.text = recordList.get(position).date
        //삭제요망
        /*holder.itemView.setOnClickListener {
            btnTrash.setOnClickListener {
                Toast.makeText(parent.context,"잘 뜬다.${DiaryRecord.title}" ,Toast.LENGTH_SHORT).show()
                removeItem(curPos)
                dbManager.delDiary(DiaryRecord.date)
            }
        }*/
    }

    override fun getItemCount(): Int {
        return recordList.size
    }

    //삭제요망
    /*
    public fun removeItem(position: Int){
        recordList.removeAt(position)
        notifyItemRemoved(position)
    }*/

    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title = itemView.findViewById<TextView>(R.id.tv_title)
        val date = itemView.findViewById<TextView>(R.id.tv_date)
    }


}