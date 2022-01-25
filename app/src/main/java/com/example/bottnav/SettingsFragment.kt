package com.example.bottnav

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.FragmentManager

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SettingsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SettingsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        val listview1 = view.findViewById<ListView>(R.id.settings_listview1)
        val listview2 = view.findViewById<ListView>(R.id.settings_listview2)
        val listview3 = view.findViewById<ListView>(R.id.settings_listview3)

        listview1.adapter = MyAdapter(view.context)
        listview2.adapter = MyAdapter2(view.context)
        listview3.adapter = MyAdapter3(view.context)

        listview1.onItemClickListener =  AdapterView.OnItemClickListener { parent, view, position, id ->

            when (position) {
                0 -> {
                    // 음량 조절 선택 시
                    val volume_fragment = VolumeDialogFragment()
                    volume_fragment.show(childFragmentManager, null)
                }
                1 -> {
                    // 팁 모아보기 선택 시
                    val tipsFrag = tipsFragment()
                    childFragmentManager.beginTransaction().add(R.id.bottom_container, tipsFrag).commit()
                }
            }


        }

        listview2.onItemClickListener =  AdapterView.OnItemClickListener { parent, view, position, id ->

            when (position) {
                0 -> {
                    // 닉네임 변경 선택 시
                }
                1 -> {
                    // 로그아웃 선택 시
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
                                        val intent = Intent(view.context, LoginActivity::class.java)
                                        startActivity(intent)
                                    })
                        }

                        builder.create()
                    }

                    logoutDialog?.show()
                }
                2 -> {
                    // 회원 탈퇴 선택 시
                }
            }

        }

        listview3.onItemClickListener =  AdapterView.OnItemClickListener { parent, view, position, id ->

            when (position) {
                0 -> {
                    // 버전 선택 시
                    // popup dialog
                    val logoutDialog: AlertDialog? = activity?.let {
                        val builder = AlertDialog.Builder(it)
                        builder.apply {
                            setTitle(R.string.settings_version)
                            setMessage("버전")
                            setPositiveButton(R.string.answer_ok, null)
                        }

                        builder.create()
                    }
                    logoutDialog?.show()
                }
                1 -> {
                    // Contact 선택 시
                }
                2 -> {
                    // About Us 선택 시
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
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private class MyAdapter(context: Context) : BaseAdapter() {

        val myContext: Context = context

        val list = arrayListOf<String>(
            context.getString(R.string.settings_sound),
            context.getString(R.string.settings_tip)
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
            val layout = layoutInflater.inflate(R.layout.settings_list, viewGroup, false)

            val list_text = layout.findViewById<TextView>(R.id.list_textView)
            list_text.text = list.get(position)

            return layout
        }

    }

    private class MyAdapter2(context: Context) : BaseAdapter() {

        val myContext: Context = context

        val list = arrayListOf<String>(
            context.getString(R.string.settings_nickname),
            context.getString(R.string.settings_logout),
            context.getString(R.string.settings_del_acc)
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
            val layout = layoutInflater.inflate(R.layout.settings_list, viewGroup, false)

            val list_text = layout.findViewById<TextView>(R.id.list_textView)
            list_text.text = list.get(position)

            return layout
        }

    }

    private class MyAdapter3(context: Context) : BaseAdapter() {

        val myContext: Context = context

        val list = arrayListOf<String>(
            context.getString(R.string.settings_version),
            context.getString(R.string.settings_contact),
            context.getString(R.string.settings_about_us)
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
            val layout = layoutInflater.inflate(R.layout.settings_list, viewGroup, false)

            val list_text = layout.findViewById<TextView>(R.id.list_textView)
            list_text.text = list.get(position)

            return layout
        }

    }
}