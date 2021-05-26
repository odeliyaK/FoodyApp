package com.foodyapp;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
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

        MyInfoManager.getInstance().openDataBase(this);

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
                if(!MyInfoManager.getInstance().isInventoryUpdated("Tenuva")) {
                    FragmentManager fm = getFragmentManager();
                    DairyActivity fragment = new DairyActivity();
                    FragmentTransaction t = fm.beginTransaction();
                    t.replace(R.id.root_layout, fragment);
                    t.addToBackStack(null);
                    t.commit();
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(InventoryActivity.this);
                    builder.setMessage(R.string.inventoryMadeToday);
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
                if(!MyInfoManager.getInstance().isInventoryUpdated("Osem")) {
                    FragmentManager fm = getFragmentManager();
                    GroceryFragment fragment = new GroceryFragment();
                    FragmentTransaction t = fm.beginTransaction();
                    t.replace(R.id.root_layout, fragment);
                    t.addToBackStack(null);
                    t.commit();
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(InventoryActivity.this);
                    builder.setMessage(R.string.inventoryMadeToday);
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
                if(!MyInfoManager.getInstance().isInventoryUpdated("Butcher")) {
                    FragmentManager fm = getFragmentManager();
                    MeatPoultryFragment fragment = new MeatPoultryFragment();
                    FragmentTransaction t = fm.beginTransaction();
                    t.replace(R.id.root_layout, fragment);
                    t.addToBackStack(null);
                    t.commit();
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(InventoryActivity.this);
                    builder.setMessage(R.string.inventoryMadeToday);
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
                if(!MyInfoManager.getInstance().isInventoryUpdated("Meshek")) {
                    FragmentManager fm = getFragmentManager();
                    FruitsVegFragment fragment = new FruitsVegFragment();
                    FragmentTransaction t = fm.beginTransaction();
                    t.replace(R.id.root_layout, fragment);
                    t.addToBackStack(null);
                    t.commit();
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(InventoryActivity.this);
                    builder.setMessage(R.string.inventoryMadeToday);
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

    protected void onPause() {
        MyInfoManager.getInstance().closeDataBase();
        super.onPause();
    }

}
