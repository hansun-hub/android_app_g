package com.example.bottnav

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView

/**
 * A fragment representing a list of Items.
 */
class tipsFragment : Fragment() {

    private var columnCount = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tips_list, container, false)

        val listview = view.findViewById<ListView>(R.id.tip_list)
        listview.adapter = TipsListViewAdapter(view.context)

        return view
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            tipsFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }

    class TipsListViewAdapter(context: Context): BaseAdapter() {
        val myContext: Context = context

        val list = arrayListOf<String>()

        // DB에서 tip 불러와 list에 저장

        override fun getCount(): Int {
            return list.size
        }

        override fun getItem(position: Int): Any {
            val selected = list.get(position)
            return selected
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, view: View?, viewGroup: ViewGroup?): View {
            val layoutInflater = LayoutInflater.from(myContext)
            val layout = layoutInflater.inflate(R.layout.fragment_tips_list, viewGroup, false)

            val list_num = layout.findViewById<TextView>(R.id.tip_number)
            val list_text = layout.findViewById<TextView>(R.id.tip_content)

            list_num.text = position.toString()
            list_text.text = list.get(position)

            return layout
        }
    }
}