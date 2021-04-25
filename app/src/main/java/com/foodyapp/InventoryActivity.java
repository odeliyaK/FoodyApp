package com.foodyapp;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


public class InventoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
        Button dairytBtn = findViewById(R.id.dairyBtn);
        dairytBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                DairyActivity fragmet2 = new DairyActivity();
                FragmentTransaction t = fm.beginTransaction();
                t.replace(R.id.root_layout, fragmet2);
                t.addToBackStack(null);
                t.commit();

            }
        });
        Button secondBtn = findViewById(R.id.bakerkyBtn);
        secondBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                FragmentManager fm = getFragmentManager();
//                SecondFragment fragmet2 = new SecondFragment();
//                FragmentTransaction t = fm.beginTransaction();
//                t.replace(R.id.root_layout, fragmet2);
//                t.addToBackStack(null);
//                t.commit();

            }
        });
    }

}
