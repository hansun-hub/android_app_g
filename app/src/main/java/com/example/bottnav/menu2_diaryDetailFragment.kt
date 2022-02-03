package com.example.bottnav

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView

class menu2_diaryDetailFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_menu2_diary_detail, container, false)

        val tite = requireArguments().getString("title")
        val date = requireArguments().getString("date")
        val ratingBar = requireArguments().getInt("star")
        val achvMission = requireArguments().getString("achvMission")
        val editImpre = requireArguments().getString("editIpre")

        val menu2Detail_editTitle2 = view.findViewById<TextView>(R.id.menu2Detail_tvTitle)
        val menu2Detail_editDate2 = view.findViewById<TextView>(R.id.menu2Detail_tvDate)
        val menu2Detail_achvMiision = view.findViewById<TextView>(R.id.menu2Detail_achvMission2)
        val menu2Detail_editlmpre2 = view.findViewById<TextView>(R.id.menu2Detail_tvImpre)
        val menu2Detail_ratingBar = view.findViewById<RatingBar>(R.id.menu2Detail_ratingBar)

        menu2Detail_editTitle2.setText(tite)
        menu2Detail_editDate2.setText(date)
        menu2Detail_achvMiision.setText(achvMission)
        menu2Detail_editlmpre2.setText(editImpre)


        return view

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment menu2_diaryDetailFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                menu2_diaryDetailFragment().apply {
                    arguments = Bundle().apply {

                    }
                }
    }
}