package com.example.bottnav

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

class SettingsDelAccFragment : Fragment() {
    // 설정 - 회원탈퇴

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("StringFormatInvalid")
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_settings_del_acc, container, false)

        val sharedPreference = requireContext().getSharedPreferences("current", Context.MODE_PRIVATE)   // sharedPreferences에서 nickname 가져오기
        val nickname = sharedPreference.getString("nickname", "")

        val delacc_tvTitle = view.findViewById<TextView>(R.id.delacc_tvTitle)           // 닉네임
        val delacc_tv1 = view.findViewById<TextView>(R.id.delacc_tv1)                   // 회원탈퇴 안내
        val delacc_tv2 = view.findViewById<TextView>(R.id.delacc_tv2)                   // "회원탈퇴 진행을 위해 비밀번호를 입력해주세요."
        val delacc_tv3 = view.findViewById<TextView>(R.id.delacc_tv3)                   // 비밀번호 인증 실패 시 안내
        val delacc_tv4 = view.findViewById<TextView>(R.id.delacc_tv4)                   // 회원탈퇴 성공 시 안내
        val delacc_edtPassword = view.findViewById<EditText>(R.id.delacc_edtPassword)   // 비밀번호 입력
        val delacc_btn1 = view.findViewById<Button>(R.id.delacc_btn1)                   // 진행
        val delacc_btn2 = view.findViewById<Button>(R.id.delacc_btn2)                   // 취소
        val delacc_btn3 = view.findViewById<Button>(R.id.delacc_btn3)                   // 입력
        val delacc_btn4 = view.findViewById<Button>(R.id.delacc_btn4)                   // 확인

        val dbManager = DBManager(requireContext())                                     // DB Manager

        // 닉네임 불러오기 (**님!)
        delacc_tvTitle.setText(getString(R.string.call_nickname, nickname) + "!")

        // 버튼 클릭에 따른 이벤트
        delacc_btn1.setOnClickListener {
            // 진행 선택 시, 불필요한 컴포넌트 없애고 필요한 컴포넌트 불러오기
            delacc_tvTitle.visibility = View.GONE
            delacc_tv1.visibility = View.GONE
            delacc_btn1.visibility = View.GONE

            delacc_tv2.visibility = View.VISIBLE
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
            delacc_tv3.visibility = View.GONE

            var userinput = delacc_edtPassword.text

            if (dbManager.getPassword() == userinput.toString()) {
                // 인증 성공

                // 불필요한 컴포넌트 없애고 필요한 컴포넌트 불러오기
                delacc_tv3.visibility = View.GONE
                delacc_tv2.visibility = View.GONE
                delacc_edtPassword.visibility = View.GONE
                delacc_btn2.visibility = View.GONE
                delacc_btn3.visibility = View.GONE

                delacc_tv4.visibility = View.VISIBLE
                delacc_btn4.visibility = View.VISIBLE

                // USERS에서 삭제, ACHIEVE&DIARY 테이블 삭제 -> Preference 삭제
                dbManager.delUser()
                val pref = requireActivity().getSharedPreferences("current", Context.MODE_PRIVATE)
                val editor = pref.edit()
                editor.clear()
                editor.apply()
            } else {
                // 실패
                delacc_edtPassword.setText("")
                delacc_tv3.visibility = View.VISIBLE
                delacc_tv3.setText(R.string.password_fail)
            }
        }

        delacc_btn4.setOnClickListener {
            // Login 화면으로 전환
            val intent = Intent(view.context, LoginActivity::class.java)

            startActivity(intent)
        }

        // Inflate the layout for this fragment
        return view
    }
}