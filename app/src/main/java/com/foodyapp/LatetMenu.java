package com.foodyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;

import com.google.firebase.auth.FirebaseAuth;

public class LatetMenu extends AppCompatActivity {

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_latet_menu);
        mAuth = FirebaseAuth.getInstance();
        ImageView rightIcon=findViewById(R.id.menu);
        ImageButton logOut = (ImageButton)  findViewById(R.id.logOut1);

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intent=new Intent(LatetMenu.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

//        rightIcon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showMenu(v);
//            }
//        });

        Button households = (Button) findViewById(R.id.households);
        households.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LatetMenu.this, HouseHoldListActivity.class);
                startActivity(intent);
            }
        });

        Button vols = (Button) findViewById(R.id.vols);
        vols.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LatetMenu.this, VolunteersListActivity.class);
                startActivity(intent);
            }
        });
    }
    private void openMainActivity(){
        Intent intent=new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void showMenu(View v){
        PopupMenu popupMenu=new PopupMenu(LatetMenu.this, v);
        popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId()== R.id.logOut){
                    openMainActivity();
                }
                return true;
            }
        });
        popupMenu.show();
    }

}