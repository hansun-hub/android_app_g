package com.example.bottnav

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_Nick = "nick"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentHome.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var nick: String? = null
    lateinit var tvNick : TextView
    lateinit var btnMusic : ImageButton
    lateinit var tvDo : TextView
    lateinit var tvDone : TextView
    lateinit var tvTip : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            nick = it.getString(ARG_Nick)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var rootView : View = inflater.inflate(R.layout.fragment_home, container, false)

        tvNick = rootView.findViewById(R.id.tvNick)
        tvNick.setText(nick)  //login페이지에서 넘어온 닉네임이 화면에 보임

        btnMusic = rootView.findViewById(R.id.btnMusic)
        tvDo = rootView.findViewById(R.id.tvDo)
        tvDone = rootView.findViewById(R.id.tvDone)
        tvTip = rootView.findViewById(R.id.tvTip)

        //음악 버튼
        btnMusic.setOnClickListener {
            //실행 중인지 확인하는 코드 추가하기
            (activity as MainActivity).Mstop()
        }

        return rootView
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FragmentHome.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_Nick, param1)
                }
            }
    }
}