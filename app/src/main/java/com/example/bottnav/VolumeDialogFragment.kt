package com.example.bottnav

import android.content.Context
import android.graphics.Point
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.*
import androidx.fragment.app.DialogFragment


// 수정수정수정필요

class VolumeDialogFragment(context: Context): DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        isCancelable = true
    }

    override fun onResume() {
        super.onResume()
        val width = resources.getDimensionPixelSize(R.dimen.dialogFragment_width)
        val height = resources.getDimensionPixelSize(R.dimen.dialogFragment_height)
        dialog?.window?.setLayout(width, height)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_volume_dialog, container, false)
        val sharedPreference = view.context.getSharedPreferences("current", Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        val volume = sharedPreference.getInt("volume", 0)

        val volume_seekBar = view.findViewById<SeekBar>(R.id.volume_seekBar)
        val volume_okbtn = view.findViewById<Button>(R.id.volume_okbtn)
        val volume_image = view.findViewById<ImageView>(R.id.volume_image)

        volume_seekBar.progress = volume

        volume_seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                var value = (progress.toDouble() / 10).toFloat()
                // seekBar 조작 중 볼륨 변경
                (activity as MainActivity).setMvolume(value)
                if (value < 0.1) {
                    // 음량이 0일 경우 이미지 변경
                    volume_image.setImageResource(R.drawable.ic_baseline_volume_off_24)
                } else {
                    volume_image.setImageResource(R.drawable.ic_baseline_volume_up_24)
                }
                Toast.makeText(context, value.toString(), Toast.LENGTH_SHORT).show()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // seekBar 터치 시작
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // seekBar 터치 종료
                editor.putInt("volume", seekBar!!.progress)
                editor.apply()
            }

        })
        volume_okbtn.setOnClickListener {
            // 확인
            this.dismiss()
        }

        return view
    }
}