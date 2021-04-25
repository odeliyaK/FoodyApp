package com.foodyapp;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.app.Fragment;


public class DairyActivity extends Fragment {

    public DairyActivity() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.activity_dairy2, container, false);

        TextView noTextView = (TextView) view.findViewById(R.id.dairyTitle);


        Bundle args = getArguments();
        if(args!=null) {
            String no = String.valueOf(args.getInt("no"));
            noTextView.setText(no);
            String name = args.getString("name");

        }
        return view;
    }
}