package com.example.bottnav

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.bottnav.databinding.DialogCharacterBinding
import java.lang.IllegalStateException

class CharacterDialogFragment(context : Context) {

    private val dialog = Dialog(context)
    //private lateinit var onClickListner : OnDialogClickListener

    fun showDialog(){
        dialog.setContentView(R.layout.dialog_character)
        dialog.window!!.setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT)
        val btnCancel : Button = dialog.findViewById(R.id.btnCancel)
        val btnShare : Button = dialog.findViewById(R.id.btnShare)

        dialog.show()

        btnCancel.setOnClickListener {
            dialog.dismiss()
        }
        btnShare.setOnClickListener {
            //val bottomSheet = BottomS
            //Toast.makeText(getActivity(), "$NICK", Toast.LENGTH_SHORT).show()
        }
    }

    /*
    lateinit var binding : DialogCharacterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //다이얼로그 생성
        var builder = androidx.appcompat.app.AlertDialog.Builder()
        var view = layoutInflater.inflate(R.layout.dialog_character,null)

        var btnCancel = view.findViewById<Button>(R.id.btnCancel)
        var btnShare = view.findViewById<Button>(R.id.btnShare)

        builder.create()
        builder.setView(view)
        builder.show()
        btnCancel.setOnClickListener {

        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DialogCharacterBinding.inflate(inflater,container,false)
        return binding.root
        /*
        var rootView : View = inflater.inflate(R.layout.fragment_home, container, false)
        btnCancel = rootView.findViewById(R.id.btnCancel)
        btnShare = rootView.findViewById(R.id.btnShare)

        btnCancel.setOnClickListener {
            dismiss()  //대화상자 닫음
        }
        btnShare.setOnClickListener {
            //모달바텀시트가 나오도록 설정
        }
        return rootView*/
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnCancel.setOnClickListener {
            dismiss()
            Toast.makeText(activity, "취소 선택됨", Toast.LENGTH_SHORT).show()
        }
        binding
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var btnCancel : Button
        return activity?.let{

            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater

            builder.setView(inflater.inflate(R.layout.dialog_character,null)).setTitle("축하합니다")
            builder.create()

        }?:throw  IllegalStateException("Activity cannot be null")
    }*/
}