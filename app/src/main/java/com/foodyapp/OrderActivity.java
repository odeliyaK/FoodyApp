package com.foodyapp;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class OrderActivity extends AppCompatActivity {
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);


        MyInfoManager.getInstance().openDataBase(this);

        Button back = (Button) findViewById(R.id.backBtn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OrderActivity.this, VolunteerMainActivity.class);
                startActivity(intent);

            }
        });

        Button dairytBtn = findViewById(R.id.dairyBtn);
        dairytBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!MyInfoManager.getInstance().checkIfOrderHappen("Tenuva")){
                    FragmentManager fm = getFragmentManager();
                    DairyActivityOrder fragment = new DairyActivityOrder();
                    FragmentTransaction t = fm.beginTransaction();
                    t.replace(R.id.root_layout, fragment);
                    t.addToBackStack(null);
                    t.commit();
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(OrderActivity.this);
                    builder.setMessage(R.string.orderMadeToday);
                    builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }

            }
        });

        Button groceryBtn = findViewById(R.id.groceryBtn);
        groceryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!MyInfoManager.getInstance().checkIfOrderHappen("Osem")) {
                    FragmentManager fm = getFragmentManager();
                    GroceryOrderFragment fragment = new GroceryOrderFragment();
                    FragmentTransaction t = fm.beginTransaction();
                    t.replace(R.id.root_layout, fragment);
                    t.addToBackStack(null);
                    t.commit();
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(OrderActivity.this);
                    builder.setMessage(R.string.orderMadeToday);
                    builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }

            }
        });

        Button mpBtn = findViewById(R.id.meatBtn);
        mpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!MyInfoManager.getInstance().checkIfOrderHappen("Butcher")) {
                    FragmentManager fm = getFragmentManager();
                    MeatPoultryOrderFragment fragment = new MeatPoultryOrderFragment();
                    FragmentTransaction t = fm.beginTransaction();
                    t.replace(R.id.root_layout, fragment);
                    t.addToBackStack(null);
                    t.commit();
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(OrderActivity.this);
                    builder.setMessage(R.string.orderMadeToday);
                    builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }

            }

        });

        Button fvBtn = findViewById(R.id.fvBtn);
        fvBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!MyInfoManager.getInstance().checkIfOrderHappen("Meshek")) {
                    FragmentManager fm = getFragmentManager();
                    FruitsVegOrder fragment = new FruitsVegOrder();
                    FragmentTransaction t = fm.beginTransaction();
                    t.replace(R.id.root_layout, fragment);
                    t.addToBackStack(null);
                    t.commit();
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(OrderActivity.this);
                    builder.setMessage(R.string.orderMadeToday);
                    builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }

            }
        });
    }

    @Override
    protected void onResume() {
        MyInfoManager.getInstance().openDataBase(this);
        super.onResume();

    }

    protected void onPause() {
        MyInfoManager.getInstance().closeDataBase();
        super.onPause();
    }

}
