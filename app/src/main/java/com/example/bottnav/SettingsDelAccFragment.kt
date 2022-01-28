package com.example.bottnav

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment

class SettingsDelAccFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("StringFormatInvalid")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_settings_del_acc, container, false)
        val sharedPreference = requireContext().getSharedPreferences("current", Context.MODE_PRIVATE)
        val nickname = sharedPreference.getString("nickname", "")

        val delacc_textView1 = view.findViewById<TextView>(R.id.delacc_textView)           // 닉네임
        val delacc_textView2 = view.findViewById<TextView>(R.id.delacc_textView2)          // 회원탈퇴 안내
        val delacc_textView3 = view.findViewById<TextView>(R.id.delacc_textView3)          // "회원탈퇴 진행을 위해 비밀번호를 입력해주세요."
        val delacc_textView4 = view.findViewById<TextView>(R.id.delacc_textView4)          // 비밀번호 인증 실패 시 안내
        val delacc_textView5 = view.findViewById<TextView>(R.id.delacc_textView5)          // 회원탈퇴 성공 시 안내
        val delacc_edtPassword = view.findViewById<EditText>(R.id.delacc_edtPassword)     // 비밀번호 입력
        val delacc_btn1 = view.findViewById<Button>(R.id.delacc_btn1)                      // 진행
        val delacc_btn2 = view.findViewById<Button>(R.id.delacc_btn2)                      // 취소
        val delacc_btn3 = view.findViewById<Button>(R.id.delacc_btn3)                      // 입력
        val delacc_btn4 = view.findViewById<Button>(R.id.delacc_btn4)                      // 확인

        val dbManager = DBManager(requireContext())

        // 닉네임 불러오기 (**님!)
        delacc_textView1.setText(getString(R.string.call_nickname, nickname) + "!")

        // 버튼 클릭에 따른 이벤트
        delacc_btn1.setOnClickListener {
            // 진행 선택 시, 불필요한 컴포넌트 없애고 필요한 컴포넌트 불러오기

            delacc_textView1.visibility = View.GONE
            delacc_textView2.visibility = View.GONE
            delacc_btn1.visibility = View.GONE

            delacc_textView3.visibility = View.VISIBLE
            delacc_edtPassword.visibility = View.VISIBLE
            delacc_btn3.visibility = View.VISIBLE
        }

        delacc_btn2.setOnClickListener {
            // 취소 선택 시 이전 화면으로 복귀
            parentFragmentManager.beginTransaction().remove(this).commit()
            parentFragmentManager.popBackStack();
        }

        delacc_btn3.setOnClickListener {
            // 입력 선택 시 입력값과 password값 비교
            delacc_textView4.visibility = View.GONE

            var userinput = delacc_edtPassword.text

            if (dbManager.getPassword().equals(userinput)) {
                // 인증 성공

                // 불필요한 컴포넌트 없애고 필요한 컴포넌트 불러오기
                delacc_textView4.visibility = View.GONE
                delacc_textView3.visibility = View.GONE
                delacc_edtPassword.visibility = View.GONE
                delacc_btn3.visibility = View.GONE

                delacc_textView5.visibility = View.VISIBLE
                delacc_btn4.visibility = View.VISIBLE

                // USERS에서 삭제, ACHIEVE&DIARY 테이블 삭제 -> Preference 삭제
                dbManager.delUser()
                val pref = requireActivity().getSharedPreferences("current", Context.MODE_PRIVATE)
                pref.edit().clear()
                pref.edit().apply()

                // Login 화면으로 전환
                val intent = Intent(view.context, LoginActivity::class.java)
                startActivity(intent)
            } else {
                // 실패
                delacc_edtPassword.setText("")
                delacc_textView4.visibility = View.VISIBLE
                delacc_textView4.setText(R.string.password_fail)
            }
        }


        // Inflate the layout for this fragment
        return view
    }

    companion object {
        /*
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SettingsBottomSheetFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SettingsBottomSheetFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }

         */
    }
}