package com.example.bottnav

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import java.util.concurrent.ThreadLocalRandom

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_Nick = "nick"  //안쓰면 지우기

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentHome.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    lateinit var tvNick : TextView
    lateinit var btnMusic : ImageButton
    lateinit var tvDo : TextView
    lateinit var tvDone : TextView
    lateinit var tvTip : TextView
    lateinit var NICK : String
    lateinit var ivSprout : ImageView
    lateinit var dbManager : DBManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            //nick = it.getString(ARG_Nick)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var rootView : View = inflater.inflate(R.layout.fragment_home, container, false)

        //SharedPreferences로 닉네임 가져오기
        val pref : SharedPreferences = requireActivity().getSharedPreferences("current", Context.MODE_PRIVATE)
        NICK = pref.getString("nickname",null).toString()
        tvNick = rootView.findViewById(R.id.tvNick)
        tvNick.setText(NICK)  //login페이지에서 넘어온 닉네임이 화면에 보임

        btnMusic = rootView.findViewById(R.id.btnMusic)
        tvDo = rootView.findViewById(R.id.tvDo)
        tvDone = rootView.findViewById(R.id.tvDone)
        tvTip = rootView.findViewById(R.id.tvTip)
        ivSprout = rootView.findViewById(R.id.ivSprout)  //tvDone수에 따라 이미지 바뀌도록 연결하기

        var isplay : Boolean = true

        //음악 버튼
        btnMusic.setOnClickListener {
            //실행 중인지 확인하는 코드 추가하기
            if(isplay){ //실행 중이라면 중단
                (activity as MainActivity).Mpause()
                btnMusic.setImageResource(R.drawable.ic_baseline_music_off_24)
                isplay = !isplay
            }
            else{
                (activity as MainActivity).Mstart()
                btnMusic.setImageResource(R.drawable.ic_baseline_music_note_24)
                isplay = !isplay
            }
        }

        //db에서 팁 배열 가져옴
        dbManager = DBManager(rootView.context)
        val tip = dbManager.getTips("tip")

        //랜덤 수 만들기
        var tipSize = (tip!!.size).toInt()
        val random = (0..tipSize-1).random()
        //Toast.makeText(getActivity(), "$random", Toast.LENGTH_SHORT).show()

        //팁 배열에서 랜덤으로 값 가져와서 text가 바뀜
        tvTip.text = tip?.get(random)



        //dialog 생성
        /*if(tvDo==tvDone){
            var builder = AlertDialog.Builder()
            var dia = layoutInflater.inflate(R.layout.dialog_character,null)
            builder.setView(dia)
        }

        val characterFragment = CharacterDialogFragment()
        characterFragment.show(childFragmentManager,null)*/


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