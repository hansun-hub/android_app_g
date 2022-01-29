package com.example.bottnav

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.widget.Button
import android.widget.SeekBar
import androidx.fragment.app.DialogFragment

class VolumeDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            // Get the layout inflater
            val inflater = requireActivity().layoutInflater;
            val view = inflater.inflate(R.layout.fragment_volume_dialog, null)

            val sharedPreference = view.context.getSharedPreferences("current", Context.MODE_PRIVATE)
            val volume = sharedPreference.getString("volume", "")

            var value = 0

            val volume_seekBar = view.findViewById<SeekBar>(R.id.volume_seekBar)
            val volume_okbtn = view.findViewById<Button>(R.id.volume_okbtn)

            volume_seekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    // seekBar 조작 중
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                    // seekBar 터치 시작
                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                    // seekBar 터치 종료
                }

            })

            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            builder.setView(view)
                // Add action buttons
                .setPositiveButton(R.string.answer_ok,
                    DialogInterface.OnClickListener { dialog, id ->
                    })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}