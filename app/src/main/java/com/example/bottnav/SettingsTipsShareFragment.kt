package com.example.bottnav

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SettingsTipsShareFragment(contents: String): BottomSheetDialogFragment() {
    val contents = contents

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_settings_tips_share_bottomsheet,container,false)
        val tips_share_okBtn = view.findViewById<Button>(R.id.tips_share_okBtn)
        val tips_share_message = view.findViewById<ImageButton>(R.id.tips_share_message)
        val tips_share_email = view.findViewById<ImageButton>(R.id.tips_share_email)

        tips_share_message.setOnClickListener {
            //메시지 보내기
            val sending_message = Intent(Intent.ACTION_SENDTO)
            sending_message.putExtra("sms_body", getString(R.string.tips_share_message, contents))
            sending_message.data = Uri.parse("smsto:"+ Uri.encode(""))
            startActivity(sending_message)
        }
        tips_share_email.setOnClickListener {
            //이메일 보내기
            val sending_email = Intent(Intent.ACTION_SEND)
            sending_email.type = "plain/text"
            sending_email.putExtra(Intent.EXTRA_EMAIL, "")
            sending_email.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.tips_share_title))
            sending_email.putExtra(Intent.EXTRA_TEXT, contents)
            startActivity(sending_email)
        }
        tips_share_okBtn.setOnClickListener {
            //취소
            parentFragmentManager.beginTransaction().remove(this).commit()
            parentFragmentManager.popBackStack()
        }
        return view
    }
}