package com.example.bottnav

import android.app.Dialog
import android.content.Context
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView

class CharacterDialog(context: Context) {
    private val dialog = Dialog(context)

    fun showDialog(res: Int) {
        dialog.setContentView(R.layout.dialog_character)
        dialog.window!!.setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.setCanceledOnTouchOutside(false)   //외부 클릭으로 사라지지 않음
        val home_btnCancel: Button = dialog.findViewById<Button>(R.id.home_btnCancel)
        val home_btnShare: Button = dialog.findViewById<Button>(R.id.home_btnShare)
        var home_ivCharacter: ImageView = dialog.findViewById<ImageView>(R.id.home_ivCharacter)

        home_ivCharacter.setImageResource(res)  //받아온 이미지로 변경
        dialog.show()

        home_btnCancel.setOnClickListener {
            dialog.dismiss()
        }
        home_btnShare.setOnClickListener {
            //홈 프래그먼트에서 바텀시트가 프래그먼트 위에 나오도록 코드 설정
            onClickedLister.onClicked()
            dialog.dismiss()  //사라짐
        }
    }

    interface ButtonClickListener {
        fun onClicked()
    }

    private lateinit var onClickedLister: ButtonClickListener

    fun setOnClickedListener(listener: ButtonClickListener) {
        onClickedLister = listener
    }

}