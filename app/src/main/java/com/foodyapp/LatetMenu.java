package com.foodyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;

public class LatetMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImageView rightIcon=findViewById(R.id.menu);

        setContentView(R.layout.activity_latet_menu);



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

}