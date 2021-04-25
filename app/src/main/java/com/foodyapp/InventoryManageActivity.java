package com.foodyapp;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class InventoryManageActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_manage);
        Button firstBtn = findViewById(R.id.dairyBtn);
        firstBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // send data to fragment
//                FragmentManager fm = getFragmentManager();
//                Bundle args = new Bundle();
//                args.putInt("no", 123);
//                args.putString("name", "Israel Israel");
//                FirstFragment fragmet1 = new FirstFragment();
//                fragmet1.setArguments(args);
//                FragmentTransaction t = fm.beginTransaction();
//                t.replace(R.id.root_layout, fragmet1);
//                t.addToBackStack(null);
//                t.commit();

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
