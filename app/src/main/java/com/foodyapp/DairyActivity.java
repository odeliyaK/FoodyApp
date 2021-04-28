package com.foodyapp;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.app.Fragment;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class DairyActivity extends Fragment implements View.OnClickListener{
    private static final int[] idArray = {R.id.plus1,R.id.plus2,R.id.plus3,R.id.plus4,R.id.minus1,R.id.minus2,R.id.minus3,R.id.minus4};
//  private static final int[] idArrayMinus = {R.id.minus1,R.id.minus2,R.id.minus3,R.id.minus4};
    private ImageButton[] buttons = new ImageButton[idArray.length];
//    private ImageButton[] minusButtons = new ImageButton[idArrayMinus.length];
    ImageButton p1,p2,p3,p4,m1,m2,m3,m4;
    EditText num1,num2,num3,num4;

    public DairyActivity() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.activity_dairy2, container, false);

//        p1 = (ImageButton) view.findViewById(R.id.plus1);
//        p2 = (ImageButton) view.findViewById(R.id.plus2);
//        p3 = (ImageButton) view.findViewById(R.id.plus3);
//        p4 = (ImageButton) view.findViewById(R.id.plus4);
        num1 = (EditText) view.findViewById(R.id.num1);
        num2 = (EditText) view.findViewById(R.id.num2);
        num3 = (EditText) view.findViewById(R.id.num3);
        num4 = (EditText) view.findViewById(R.id.num4);
//        m1 = (ImageButton) view.findViewById(R.id.minus1);
//        m2 = (ImageButton) view.findViewById(R.id.minus2);
//        m3 = (ImageButton) view.findViewById(R.id.minus3);
//        m4 = (ImageButton) view.findViewById(R.id.minus4);

        for(int i=0; i<idArray.length; i++) {
            buttons[i] = (ImageButton) view.findViewById(idArray[i]);
            buttons[i].setOnClickListener(this);
        }
        return view;
    }

    @Override
    public void onClick(View v) {
        int num = 0;
        switch (v.getId()){
            case R.id.plus1:
                num = 0;
                    num = Integer.parseInt(num1.getText().toString());
                num++;
                num1.setText(String.valueOf(num));
                Toast.makeText(getActivity(),"plus1",Toast.LENGTH_SHORT).show();
                break;
            case R.id.plus2:
                num = 0;
                    num = Integer.parseInt(num2.getText().toString());
                num++;
                num2.setText(String.valueOf(num));
                break;
            case R.id.plus3:
                num = 0;
                num = Integer.parseInt(num3.getText().toString());
                num++;
                num3.setText(String.valueOf(num));
                break;
            case R.id.plus4:
                num = 0;
                    num = Integer.parseInt(num4.getText().toString());
                num++;
                num4.setText(String.valueOf(num));
                break;
            case R.id.minus1:
                num = 0;
                    num = Integer.parseInt(num1.getText().toString());
                num--;
                num1.setText(String.valueOf(num));
                break;
            case R.id.minus2:
                num = 0;
                    num = Integer.parseInt(num2.getText().toString());
                num--;
                num2.setText(String.valueOf(num));
                break;
            case R.id.minus3:
                num = 0;
                    num = Integer.parseInt(num3.getText().toString());
                num--;
                num3.setText(String.valueOf(num));
                break;
            case R.id.minus4:
                num = 0;
                    num = Integer.parseInt(num4.getText().toString());
                num--;
                num4.setText(String.valueOf(num));
                break;
        }
    }
}