package com.example.bottnav

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SettingsContactFragment : BottomSheetDialogFragment() {
    // 설정 - contact

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("StringFormatInvalid")
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.settings_contact, container, false)

        val contact_btnOk = view.findViewById<Button>(R.id.contact_btnOk)
        val contact_imgBtnCall = view.findViewById<ImageButton>(R.id.contact_imgBtnCall)
        val contact_imgBtnMail = view.findViewById<ImageButton>(R.id.contact_imgBtnMail)

        val dbManager = DBManager(requireContext())

        contact_imgBtnCall.setOnClickListener {
            // 전화
            val call = Intent(Intent.ACTION_DIAL, Uri.parse("tel:01012345678"))

            startActivity(call)
        }

        contact_imgBtnMail.setOnClickListener {
            // 이메일
            val sending_email = Intent(Intent.ACTION_SEND)
            val address = arrayOf("email@address.com")

            sending_email.type = "plain/text"
            sending_email.putExtra(Intent.EXTRA_EMAIL, address)
            sending_email.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.settings_contact_title))

            startActivity(sending_email)
        }

        contact_btnOk.setOnClickListener {
            // 확인
            parentFragmentManager.beginTransaction().remove(this).commit()
            parentFragmentManager.popBackStack()
        }

        // Inflate the layout for this fragment
        return view
    }
}