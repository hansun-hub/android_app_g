package com.example.bottnav

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SettingsContactFragment : BottomSheetDialogFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("StringFormatInvalid")
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.settings_contact, container, false)

        val contact_okBtn = view.findViewById<Button>(R.id.contact_okBtn)
        val contactImgBtn1 = view.findViewById<ImageButton>(R.id.contact_imgBtn1)
        val contactImgBtn2 = view.findViewById<ImageButton>(R.id.contact_imgBtn2)

        val dbManager = DBManager(requireContext())

        contactImgBtn1.setOnClickListener {
            // 전화
            val call = Intent(Intent.ACTION_DIAL, Uri.parse("tel:01012345678"))
            startActivity(call)
        }
        contactImgBtn2.setOnClickListener {
            // 이메일
            val sending_email = Intent(Intent.ACTION_SEND)
            sending_email.type = "plain/text"
            val address = "email@address.com"
            sending_email.putExtra(Intent.EXTRA_EMAIL, address)
            sending_email.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.settings_contact_title))
            startActivity(sending_email)
        }
        contact_okBtn.setOnClickListener {
            // 확인
            parentFragmentManager.beginTransaction().remove(this).commit()
            parentFragmentManager.popBackStack()
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