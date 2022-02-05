package com.example.bottnav

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.setPadding


class SettingsTipsFragment : Fragment() {
    // 설정 - 팁 모아보기

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_settings_tips, container, false)
        val settings_listTips = view.findViewById<ListView>(R.id.settings_listTips)
        val adapter = MyAdapter(view.context)

        // 어댑터 연결
        settings_listTips.adapter = adapter
        settings_listTips.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                val contents = adapter.list?.get(position)
                val shareFrag = SettingsTipsShareFragment(contents!!)
                shareFrag.show(childFragmentManager, shareFrag.tag)
            }

        return view
    }

    private class MyAdapter(context: Context) : BaseAdapter() {
        //ListView 어댑터

        val myContext: Context = context
        val dbManager = DBManager(context)

        val list = dbManager.getTips("tip")

        override fun getCount(): Int {
            if (list != null) {
                return list.size
            }

            return 0
        }

        override fun getItem(position: Int): String {
            val selected = list!![position]
            return selected
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        @SuppressLint("ViewHolder")
        override fun getView(position: Int, view: View?, viewGroup: ViewGroup?): View {
            val layoutInflater = LayoutInflater.from(myContext)
            val layout =
                layoutInflater.inflate(R.layout.layout_settings_list_item, viewGroup, false)
            val tips_list_tv = layout.findViewById<TextView>(R.id.tips_list_tv)

            tips_list_tv.setPadding(20)
            tips_list_tv.text = "${position + 1}. ${list!!.get(position)}"

            return layout
        }

    }
}