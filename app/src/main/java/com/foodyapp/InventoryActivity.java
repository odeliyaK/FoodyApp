package com.foodyapp;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;


public class InventoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        Button back = (Button) findViewById(R.id.backBtn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(InventoryActivity.this, VolunteerMainActivity.class);
                startActivity(intent);

            }
        });

        Button dairytBtn = findViewById(R.id.dairyBtn);
        dairytBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                DairyActivity fragment = new DairyActivity();
                FragmentTransaction t = fm.beginTransaction();
                t.replace(R.id.root_layout, fragment);
                t.addToBackStack(null);
                t.commit();

            }
        });
        Button groceryBtn = findViewById(R.id.groceryBtn);
        groceryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                GroceryFragment fragment = new GroceryFragment();
                FragmentTransaction t = fm.beginTransaction();
                t.replace(R.id.root_layout, fragment);
                t.addToBackStack(null);
                t.commit();

            }
        });

        Button mpBtn = findViewById(R.id.meatBtn);
        mpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                MeatPoultryFragment fragment = new MeatPoultryFragment();
                FragmentTransaction t = fm.beginTransaction();
                t.replace(R.id.root_layout, fragment);
                t.addToBackStack(null);
                t.commit();

            }
        });

        Button fvBtn = findViewById(R.id.fvBtn);
        fvBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                FruitsVegFragment fragment = new FruitsVegFragment();
                FragmentTransaction t = fm.beginTransaction();
                t.replace(R.id.root_layout, fragment);
                t.addToBackStack(null);
                t.commit();

            }
        });
    }

}
