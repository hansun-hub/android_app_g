package com.example.bottnav

import android.app.Dialog
import android.content.Context
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView

class CharacterDialog(context: Context) {
    private val dialog = Dialog(context)
    //private lateinit var onClickListner : OnDialogClickListener

    fun showDialog(res : Int){
        dialog.setContentView(R.layout.dialog_character)
        dialog.window!!.setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.setCanceledOnTouchOutside(false)   //외부 클릭으로 사라지지 않음.
        val home_btnCancel : Button = dialog.findViewById(R.id.home_btnCancel)
        val home_btnShare : Button = dialog.findViewById(R.id.home_btnShare)
        var home_ivCharacter : ImageView = dialog.findViewById(R.id.home_ivCharacter)

        home_ivCharacter.setImageResource(res)  //받아온 이미지로 변경
        dialog.show()

        home_btnCancel.setOnClickListener {
            dialog.dismiss()
        }
        home_btnShare.setOnClickListener {
            //바텀시트가 프래그먼트 위에 나오도록 코드 설정하기
            //val bottomSheet = CharacterBottomSheetFragment(rootContext)  //BottomSheetDialog에 MainActivity컨텍스트 전달
            //bottomSheet.show()
            //Toast.makeText(getActivity(), "$NICK", Toast.LENGTH_SHORT).show()
            onClickedLister.onClicked()
            dialog.dismiss()  //사라짐
        }
    }

    interface ButtonClickListener{
        fun onClicked()
    }
    private lateinit var onClickedLister : ButtonClickListener

    fun setOnClickedListener(listener : ButtonClickListener){
        onClickedLister = listener
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