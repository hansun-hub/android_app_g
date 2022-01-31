package com.example.bottnav

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CharacterBottomSheetFragment() : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_character_bottomsheet,container,false)
        val home_share_okBtn = view.findViewById<Button>(R.id.home_share_okBtn)
        val home_share_message = view.findViewById<ImageButton>(R.id.home_share_message)
        val home_share_email = view.findViewById<ImageButton>(R.id.home_share_email)

        home_share_message.setOnClickListener {
            //메시지 보내기
            val sending_message = Intent(Intent.ACTION_SENDTO)
            sending_message.putExtra("sms_body", getString(R.string.share_message))
            sending_message.setData(Uri.parse("smsto:"+Uri.encode("")))
            startActivity(sending_message)
            home_share_okBtn.setText(getString(R.string.answer_ok))  //버튼 텍스트 "확인"으로 바꿈
            home_share_okBtn.setOnClickListener {
                //확인 버튼
                parentFragmentManager.beginTransaction().remove(this).commit()
                parentFragmentManager.popBackStack()
            }
        }
        home_share_email.setOnClickListener {
            //이메일 보내기
            val sending_email = Intent(Intent.ACTION_SEND)
            sending_email.type = "plain/text"
            sending_email.putExtra(Intent.EXTRA_EMAIL, "")
            sending_email.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.shareWithFriend))
            sending_email.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_message))
            startActivity(sending_email)
            home_share_okBtn.setText(getString(R.string.answer_ok))  //버튼 텍스트 "확인"으로 바꿈
            home_share_okBtn.setOnClickListener {
                //확인 버튼
                parentFragmentManager.beginTransaction().remove(this).commit()
                parentFragmentManager.popBackStack()
            }
        }
        home_share_okBtn.setOnClickListener {
            //취소 버튼
            parentFragmentManager.beginTransaction().remove(this).commit()
            parentFragmentManager.popBackStack()
            Toast.makeText(getActivity(), "공유 취소됨", Toast.LENGTH_SHORT).show()
        }
        return view
    }
}