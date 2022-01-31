package com.example.bottnav

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.bottnav.databinding.FragmentEditTodoDialogBinding
import java.time.LocalDateTime

class EditTodoFragment : Fragment() {
    lateinit var binding: FragmentEditTodoDialogBinding
    lateinit var mainActivity: MainActivity
    lateinit var sqlDB : SQLiteDatabase
    lateinit var myHelper: DBHelper

    lateinit var radioPeriod: RadioGroup
    lateinit var rb_today : RadioButton
    lateinit var rb_week : RadioButton
    lateinit var rb_month : RadioButton
    lateinit var editTextTodo : EditText
    lateinit var button_make_todo : Button
    lateinit var myDB : DBManager

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is MainActivity) mainActivity = context
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentEditTodoDialogBinding.inflate(inflater, container, false)

        radioPeriod = binding.root.findViewById(R.id.radioPeriod)
        editTextTodo = binding.root.findViewById(R.id.editTextTodo)
        rb_today = binding.root.findViewById(R.id.rb_today)
        rb_week = binding.root.findViewById(R.id.rb_week)
        rb_month = binding.root.findViewById(R.id.rb_month)
        button_make_todo = binding.root.findViewById(R.id.button_make_todo)
        myDB = DBManager(mainActivity)

        var contentTodo : String
        var periodTodo : String = ""

        radioPeriod.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){

                R.id.rb_today -> {
                    periodTodo = "D"
                }
                R.id.rb_week -> {
                    periodTodo = "W"
                }
                R.id.rb_month -> {
                    periodTodo = "M"
                }
            }
        }

        binding.buttonMakeTodo.setOnClickListener {
            contentTodo = editTextTodo.getText().toString()
            if ( contentTodo.length == 0 ) {
                //공백일 때 처리할 내용
                Toast.makeText(getActivity(), "입력하세요", Toast.LENGTH_LONG).show();


            } else {
                //공백이 아닐 때 처리할 내용
                myDB.addCustomChallenge(LocalDateTime.now().toString(), contentTodo, periodTodo)
            }
            mainActivity.onBackPressed()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
}