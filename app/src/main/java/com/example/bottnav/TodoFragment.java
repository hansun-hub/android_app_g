package com.example.bottnav;

//**********파일 삭제 필요**********
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class TodoFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_completed_item, container, false);

        initUI(rootView);

        return rootView;
    }

    private void initUI(ViewGroup rootView){

    }

}
