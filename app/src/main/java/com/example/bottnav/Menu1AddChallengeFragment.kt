package com.example.bottnav

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.bottnav.databinding.FragmentMenu1AddTodoBinding
import java.time.LocalDate

class Menu1AddChallengeFragment : Fragment() {
    lateinit var binding: FragmentMenu1AddTodoBinding
    lateinit var mainActivity: MainActivity

    lateinit var editTextTodo: EditText
    lateinit var button_make_todo: Button
    lateinit var myDB: DBManager

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainActivity) mainActivity = context
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMenu1AddTodoBinding.inflate(inflater, container, false)

        editTextTodo = binding.root.findViewById(R.id.editTextTodo)
        button_make_todo = binding.root.findViewById(R.id.button_make_todo)
        myDB = DBManager(mainActivity)

        var contentTodo: String

        binding.buttonMakeTodo.setOnClickListener {
            contentTodo = editTextTodo.text.toString()

            if (contentTodo.length == 0) {
                // 공백일 때 처리할 내용
                Toast.makeText(activity, "입력하세요", Toast.LENGTH_LONG).show();
            } else {
                // 공백이 아닐 때 처리할 내용
                myDB.addCustomChallenge(LocalDate.now().toString(), contentTodo)

                val menu1fragment = Menu1Fragment()
                mainActivity.replaceFragmentExit(menu1fragment)
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
}