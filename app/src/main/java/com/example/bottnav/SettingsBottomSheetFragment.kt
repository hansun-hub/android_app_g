package com.example.bottnav

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SettingsBottomSheetFragment : BottomSheetDialogFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("StringFormatInvalid")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_settings_bottom_sheet, container, false)
        val sharedPreference = requireContext().getSharedPreferences("current", Context.MODE_PRIVATE)
        val nickname = sharedPreference.getString("nickname", "")

        val bsheet_textView1 = view.findViewById<TextView>(R.id.bsheet_textView)           // 닉네임
        val bsheet_textView2 = view.findViewById<TextView>(R.id.bsheet_textView2)          // 회원탈퇴 안내
        val bsheet_textView3 = view.findViewById<TextView>(R.id.bsheet_textView3)          // "회원탈퇴 진행을 위해 비밀번호를 입력해주세요."
        val bsheet_textView4 = view.findViewById<TextView>(R.id.bsheet_textView4)          // 비밀번호 인증 실패 시 안내
        val bsheet_textView5 = view.findViewById<TextView>(R.id.bsheet_textView5)          // 회원탈퇴 성공 시 안내
        val bsheet_editPassword = view.findViewById<EditText>(R.id.bsheet_edtPassword)     // 비밀번호 입력
        val bsheet_btn1 = view.findViewById<Button>(R.id.bsheet_btn1)                      // 진행
        val bsheet_btn2 = view.findViewById<Button>(R.id.bsheet_btn2)                      // 취소
        val bsheet_btn3 = view.findViewById<Button>(R.id.bsheet_btn3)                      // 입력
        val bsheet_btn4 = view.findViewById<Button>(R.id.bsheet_btn4)                      // 확인

        val dbManager = DBManager(requireContext())

        // 닉네임 불러오기 (**님!)
        bsheet_textView1.setText(getString(R.string.settings_nickname, nickname) + "!")

        // 버튼 클릭에 따른 이벤트
        bsheet_btn1.setOnClickListener {
            // 진행 선택 시, 불필요한 컴포넌트 없애고 필요한 컴포넌트 불러오기

            bsheet_textView1.visibility = View.GONE
            bsheet_textView2.visibility = View.GONE
            bsheet_btn1.visibility = View.GONE

            bsheet_textView3.visibility = View.VISIBLE
            bsheet_editPassword.visibility = View.VISIBLE
            bsheet_btn3.visibility = View.VISIBLE
        }

        bsheet_btn2.setOnClickListener {
            // 취소 선택 시 이전 화면으로 복귀
            requireActivity().supportFragmentManager.popBackStack()
        }

        bsheet_btn3.setOnClickListener {
            // 입력 선택 시 입력값과 password값 비교

            var userinput = bsheet_editPassword.text

            if (dbManager.getPassword().equals(userinput)) {
                // 인증 성공

                // 불필요한 컴포넌트 없애고 필요한 컴포넌트 불러오기
                bsheet_textView4.visibility = View.GONE
                bsheet_textView3.visibility = View.GONE
                bsheet_editPassword.visibility = View.GONE
                bsheet_btn3.visibility = View.GONE

                bsheet_textView5.visibility = View.VISIBLE
                bsheet_btn4.visibility = View.VISIBLE

                // USERS에서 삭제, ACHIEVE&DIARY 테이블 삭제 -> Preference 삭제
                val pref = requireActivity().getSharedPreferences("current", Context.MODE_PRIVATE)
                pref.edit().clear()
                pref.edit().apply()

                // Login 화면으로 전환
                val intent = Intent(view.context, LoginActivity::class.java)
                startActivity(intent)
                val array = ArrayList<String>(R.array.WARNINGS)[3]
                print(array)
            } else {
                // 실패
                bsheet_editPassword.setText("")
                bsheet_textView4.setText(R.string.password_fail)
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