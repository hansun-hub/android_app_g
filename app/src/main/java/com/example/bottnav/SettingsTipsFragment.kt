package com.example.bottnav

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.view.setPadding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class SettingsTipsFragment : Fragment() {
    // 설정 - 팁 모아보기

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_settings_tips, container, false)
        val settings_listTips = view.findViewById<ListView>(R.id.settings_listTips)
        val adapter = MyAdapter(view.context)

        // 어댑터 연결
        settings_listTips.adapter = adapter
        settings_listTips.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            var contents = adapter.list?.get(position)
            val shareFrag = SettingsTipsShareFragment(contents!!)
            shareFrag.show(childFragmentManager, shareFrag.tag)
        }

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SettingsTipsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                SettingsTipsFragment().apply {
                }
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
            val selected = list!!.get(position)
            return selected
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, view: View?, viewGroup: ViewGroup?): View {
            val layoutInflater = LayoutInflater.from(myContext)
            val layout = layoutInflater.inflate(R.layout.settings_tips_list, viewGroup, false)
            val tips_list_tv = layout.findViewById<TextView>(R.id.tips_list_tv)

            tips_list_tv.setPadding(20)
            tips_list_tv.text = "${position + 1}. ${list!!.get(position)}"

            return layout
        }

    }
}