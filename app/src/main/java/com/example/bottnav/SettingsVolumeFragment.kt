package com.example.bottnav

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.DialogFragment

class SettingsVolumeFragment(context: Context): DialogFragment() {
    // 설정 - 배경음악 음량 조절

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 외부 터치로 닫을 수 있도록
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

        val view = inflater.inflate(R.layout.dialog_settings_volume, container, false)
        val sharedPreference = view.context.getSharedPreferences("current", Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        val volume = sharedPreference.getInt("volume", 0)

        val volume_seekBar = view.findViewById<SeekBar>(R.id.volume_seekBar)
        val volume_btnOk = view.findViewById<Button>(R.id.volume_btnOk)
        val volume_img = view.findViewById<ImageView>(R.id.volume_img)

        volume_seekBar.progress = volume

        volume_seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                var value = (progress.toDouble() / 10).toFloat()
                // seekBar 조작 중 볼륨 변경
                (activity as MainActivity).setMvolume(value)
                if (value < 0.1) {
                    // 음량이 0일 경우 이미지 변경
                    volume_img.setImageResource(R.drawable.ic_baseline_volume_off_24)
                } else {
                    volume_img.setImageResource(R.drawable.ic_baseline_volume_up_24)
                }
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
        volume_btnOk.setOnClickListener {
            // 확인
            this.dismiss()
        }

        return view
    }
}