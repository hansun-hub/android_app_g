package com.example.bottnav

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bottnav.databinding.FragmentMenu2Binding
import com.example.bottnav.databinding.FragmentWriteBinding

class WriteFragment : Fragment() {

    lateinit var binding: FragmentWriteBinding //수정함
    lateinit var mainActivity: MainActivity

    //추가
    override fun onAttach(context: Context) { //context에는 나를 삽입한 activity가 담겨있음 mainActivity가 맞다면 담아놓고 쓰겠음
        super.onAttach(context)
        if(context is MainActivity) mainActivity = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentWriteBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener{
            mainActivity.goBack()
        }
    }

}