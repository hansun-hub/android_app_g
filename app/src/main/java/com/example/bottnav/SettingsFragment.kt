    package com.example.bottnav

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment

class SettingsFragment : Fragment() {
    // 설정

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        val dbManager = DBManager(view.context)
        val sharedPreference = view.context.getSharedPreferences("current", Context.MODE_PRIVATE)
        val email = sharedPreference.getString("email", "")
        var nickname = sharedPreference.getString("nickname", "")
        var date = sharedPreference.getString("date", "")

        val settings_btn1 = view.findViewById<Button>(R.id.settings_btn1)   // 닉네임 변경
        val settings_btn2 = view.findViewById<Button>(R.id.settings_btn2)   // 회원 탈퇴
        val listview = view.findViewById<ListView>(R.id.settings_listview)  // 메뉴 리스트

        settings_btn1.text = getString(R.string.call_nickname, nickname)

        settings_btn1.setOnClickListener {

            // 닉네임 변경
            val nicknameDialog = AlertDialog.Builder(it.context)

            nicknameDialog.apply {
                setTitle(R.string.settings_nickname)
                setMessage(R.string.settings_nickname_message)

                val input = EditText(it.context)
                setView(input)

                setNegativeButton(getString(R.string.answer_enter)) { dialog, which ->
                    var newNickname = input.text.toString()

                    // nickname 대입
                    nickname = newNickname

                    // sharedPreference 수정
                    val editor = sharedPreference.edit()
                    editor.remove("nickname")
                    editor.putString("nickname", nickname)
                    editor.apply()

                    // DB 수정
                    dbManager.setNickname(nickname!!)

                    // info 수정
                    settings_btn1.text = getString(R.string.call_nickname, nickname)

                    // 알림
                    Toast.makeText(it.context, "닉네임이 변경되었습니다.", Toast.LENGTH_SHORT).show()
                }
                setPositiveButton(getString(R.string.answer_cancel)) { dialog, which ->
                    dialog.cancel()
                }

                nicknameDialog.show()
            }
        }

        settings_btn2.setOnClickListener {
            // 회원 탈퇴 fragment 실행
            val del_acc_frag = SettingsDelAccFragment()
            (activity as MainActivity).replaceFragment(del_acc_frag)
        }

        listview.adapter = MyAdapter(view.context)
        listview.onItemClickListener =  AdapterView.OnItemClickListener { parent, view, position, id ->
            // 메뉴 리스트
            when (position) {
                0 -> {
                    // 음량 조절 선택 시
                    val volume_fragment = VolumeDialogFragment(view.context)
                    volume_fragment.show(childFragmentManager, null)
                }
                1 -> {
                    // 팁 모아보기 선택 시
                    val tipsFrag = SettingsTipsFragment()
                    (activity as MainActivity).replaceFragment(tipsFrag)
                }
                2 -> {
                    // 버전 선택 시
                    // popup dialog
                    val versionDialog: AlertDialog? = activity?.let {
                        val builder = AlertDialog.Builder(it)
                        builder.apply {
                            setTitle(R.string.settings_version)
                            setMessage(R.string.setting_version_message)
                            setPositiveButton(R.string.answer_ok, null)
                        }

                        builder.create()
                    }
                    versionDialog?.show()
                }
                3 -> {
                    // Contact 선택 시
                    val contactFrag = SettingsContactFragment()
                    contactFrag.show(childFragmentManager, contactFrag.tag)
                }
                4 -> {
                    // About Us 선택 시
                    val aboutUsDialog: AlertDialog? = activity?.let {
                        val builder = AlertDialog.Builder(it)
                        builder.apply {
                            setTitle(R.string.settings_about_us)
                            setMessage(R.string.about_us_message)
                            setPositiveButton(R.string.answer_ok, null)
                        }

                        builder.create()
                    }
                    aboutUsDialog?.show()

                    var settings_tvDialogAboutUs = aboutUsDialog?.findViewById<TextView>(android.R.id.message)
                    settings_tvDialogAboutUs?.typeface = Typeface.createFromAsset(view.context.assets,"jua_regular.ttf")
                }
                5 -> {
                    // Log out 선택 시
                    // popup dialog
                    val logoutDialog: AlertDialog? = activity?.let {
                        val builder = AlertDialog.Builder(it)
                        builder.apply {
                            setTitle(R.string.settings_logout)
                            setMessage(R.string.settings_logout_dialog)
                            setPositiveButton(R.string.answer_no,
                                    DialogInterface.OnClickListener { dialog, id ->
                                        // 로그아웃 취소 선택 시
                                    })
                            setNegativeButton(R.string.answer_yes,
                                    DialogInterface.OnClickListener { dialog, id ->
                                        // 로그아웃 진행 선택 시
                                        // Preference 삭제
                                        val pref = requireActivity().getSharedPreferences(
                                                "current",
                                                Context.MODE_PRIVATE
                                        )
                                        val editor = pref.edit()
                                        editor.clear()
                                        editor.apply()

                                        Toast.makeText(view.context, "정상적으로 로그아웃 되었습니다.", Toast.LENGTH_SHORT).show()

                                        // Login 화면으로 전환
                                        val intent = Intent(view.context, LoginActivity::class.java)
                                        (activity as MainActivity).Mstop()
                                        startActivity(intent)
                                    })
                        }
                        builder.create()
                    }
                    logoutDialog?.show()

                    var settings_tvDialogLogout = logoutDialog?.findViewById<TextView>(android.R.id.message)
                    settings_tvDialogLogout?.typeface = Typeface.createFromAsset(view.context.assets,"jua_regular.ttf")
                }
            }
        }

        // Inflate the layout for this fragment
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SettingsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SettingsFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }


    private class MyAdapter(context: Context) : BaseAdapter() {
        // ListView 어댑터

        val myContext: Context = context

        val list = arrayListOf<String>(
                context.getString(R.string.settings_sound),
                context.getString(R.string.settings_tip),
                context.getString(R.string.settings_version),
                context.getString(R.string.settings_contact),
                context.getString(R.string.settings_about_us),
                context.getString(R.string.settings_logout)
        )

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
            val layout = layoutInflater.inflate(R.layout.settings_tips_list, viewGroup, false)

            val tips_list_tv = layout.findViewById<TextView>(R.id.tips_list_tv)
            tips_list_tv.textSize = 20F
            tips_list_tv.text = list.get(position)

            return layout
        }

    }
}