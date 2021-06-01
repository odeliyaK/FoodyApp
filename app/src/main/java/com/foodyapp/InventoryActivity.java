package com.foodyapp;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;

import androidx.appcompat.app.AppCompatActivity;


public class InventoryActivity extends AppCompatActivity {
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
        ImageView toolBarArrow=findViewById(R.id.arrow);
        ImageView rightIcon=findViewById(R.id.menu);
        toolBarArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openVolunteerActivity();
            }
        });

        rightIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenu(v);
            }
        });
        this.context = this;

        MyInfoManager.getInstance().openDataBase(this);

        Button back = (Button) findViewById(R.id.backBtn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(InventoryActivity.this, VolunteerMainActivity.class);
                startActivity(intent);

            }
        });

        Button groceryBtn = findViewById(R.id.groceryBtn);
        Button mpBtn = findViewById(R.id.meatBtn);
        Button fvBtn = findViewById(R.id.fvBtn);
        Button dairytBtn = findViewById(R.id.dairyBtn);
        dairytBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(!MyInfoManager.getInstance().isInventoryUpdated("Tenuva")) {
                    dairytBtn.setBackgroundColor(getResources().getColor(R.color.table_color));
                    groceryBtn.setBackgroundColor(getResources().getColor(R.color.teal_700));
                    mpBtn.setBackgroundColor(getResources().getColor(R.color.teal_700));
                    fvBtn.setBackgroundColor(getResources().getColor(R.color.teal_700));
                    FragmentManager fm = getFragmentManager();
                    DairyActivity fragment = new DairyActivity();
                    FragmentTransaction t = fm.beginTransaction();
                    t.replace(R.id.root_layout, fragment);
                    t.addToBackStack(null);
                    t.commit();
//                }
//                else {
//                    AlertDialog.Builder builder = new AlertDialog.Builder(InventoryActivity.this);
//                    builder.setMessage(R.string.inventoryMadeToday);
//                    builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//                        }
//                    });
//                    AlertDialog dialog = builder.create();
//                    dialog.show();
//                }

            }
        });

        groceryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(!MyInfoManager.getInstance().isInventoryUpdated("Osem")) {
                    groceryBtn.setBackgroundColor(getResources().getColor(R.color.table_color));
                    dairytBtn.setBackgroundColor(getResources().getColor(R.color.teal_700));
                    mpBtn.setBackgroundColor(getResources().getColor(R.color.teal_700));
                    fvBtn.setBackgroundColor(getResources().getColor(R.color.teal_700));
                    FragmentManager fm = getFragmentManager();
                    GroceryFragment fragment = new GroceryFragment();
                    FragmentTransaction t = fm.beginTransaction();
                    t.replace(R.id.root_layout, fragment);
                    t.addToBackStack(null);
                    t.commit();
//                }
//                else {
//                    AlertDialog.Builder builder = new AlertDialog.Builder(InventoryActivity.this);
//                    builder.setMessage(R.string.inventoryMadeToday);
//                    builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//                        }
//                    });
//                    AlertDialog dialog = builder.create();
//                    dialog.show();
//                }
            }
        });

        mpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(!MyInfoManager.getInstance().isInventoryUpdated("Butcher")) {
                    mpBtn.setBackgroundColor(getResources().getColor(R.color.table_color));
                    dairytBtn.setBackgroundColor(getResources().getColor(R.color.teal_700));
                    groceryBtn.setBackgroundColor(getResources().getColor(R.color.teal_700));
                    fvBtn.setBackgroundColor(getResources().getColor(R.color.teal_700));
                    FragmentManager fm = getFragmentManager();
                    MeatPoultryFragment fragment = new MeatPoultryFragment();
                    FragmentTransaction t = fm.beginTransaction();
                    t.replace(R.id.root_layout, fragment);
                    t.addToBackStack(null);
                    t.commit();
//                }
//                else {
//                    AlertDialog.Builder builder = new AlertDialog.Builder(InventoryActivity.this);
//                    builder.setMessage(R.string.inventoryMadeToday);
//                    builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//                        }
//                    });
//                    AlertDialog dialog = builder.create();
//                    dialog.show();
//                }

            }
        });

        fvBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(!MyInfoManager.getInstance().isInventoryUpdated("Meshek")) {
                    fvBtn.setBackgroundColor(getResources().getColor(R.color.table_color));
                    dairytBtn.setBackgroundColor(getResources().getColor(R.color.teal_700));
                    groceryBtn.setBackgroundColor(getResources().getColor(R.color.teal_700));
                    mpBtn.setBackgroundColor(getResources().getColor(R.color.teal_700));
                    FragmentManager fm = getFragmentManager();
                    FruitsVegFragment fragment = new FruitsVegFragment();
                    FragmentTransaction t = fm.beginTransaction();
                    t.replace(R.id.root_layout, fragment);
                    t.addToBackStack(null);
                    t.commit();
//                }
//                else {
//                    AlertDialog.Builder builder = new AlertDialog.Builder(InventoryActivity.this);
//                    builder.setMessage(R.string.inventoryMadeToday);
//                    builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//                        }
//                    });
//                    AlertDialog dialog = builder.create();
//                    dialog.show();
//                }

            }
        });
    }

    protected void onPause() {
        MyInfoManager.getInstance().closeDataBase();
        super.onPause();
    }

    @Override
    protected void onResume() {
        MyInfoManager.getInstance().openDataBase(this);
        super.onResume();

    }

    private void openVolunteerActivity(){
        Intent intent=new Intent(this, VolunteerMainActivity.class);
        startActivity(intent);
    }
    public void openOrdersActivity(){
        Intent intent=new Intent(this, UsersActivity.class);
        startActivity(intent);
    }

    public void openInventoryActivity(){
        Intent intent=new Intent(this, InventoryActivity.class);
        startActivity(intent);
    }

    public void openOrderFoodActivity(){
        Intent intent=new Intent(this, OrderActivity.class);
        startActivity(intent);
    }
    private void openMainActivity(){
        Intent intent=new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    private void openHistoryActivity() {
        Intent intent=new Intent(this, HistoryActivity.class);
        startActivity(intent);
    }
    private void showMenu(View v){
        PopupMenu popupMenu=new PopupMenu(InventoryActivity.this, v);
        popupMenu.getMenuInflater().inflate(R.menu.popup_menu_volunteer, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId()== R.id.manage){
                    openOrdersActivity();
                }
                else if (item.getItemId()== R.id.orderFood){
                    openOrderFoodActivity();
                }
                else if (item.getItemId()== R.id.inventoryManagement){
                    openInventoryActivity();
                }
                else if (item.getItemId()== R.id.packageHistory){
                    openHistoryActivity();
                }
                else if (item.getItemId()== R.id.logOut){
                    openMainActivity();
                }
                return true;
            }
        });
        popupMenu.show();
    }

}
