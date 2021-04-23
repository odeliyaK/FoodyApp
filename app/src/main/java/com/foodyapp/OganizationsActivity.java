package com.foodyapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class OganizationsActivity extends AppCompatActivity {
    ImageButton latet_btn;
    ImageButton pithonlev_btn;
    ImageButton yadeliezer_btn;
    ImageButton lasova_btn;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oganizations);
        this.context=this;


        latet_btn=(ImageButton)findViewById(R.id.latet_btn);
        latet_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setMessage("Are you sure ?");
                builder.setTitle("choose organization");
                builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        openOrdersActivity();
                    }
                });

                builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context, "user canceled", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        pithonlev_btn=(ImageButton)findViewById(R.id.pithonlev_btn);
        pithonlev_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
        yadeliezer_btn=(ImageButton)findViewById(R.id.yadeliezer_btn);
        yadeliezer_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
        lasova_btn=(ImageButton)findViewById(R.id.lasova_btn);
        lasova_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
    }

    public void openDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setMessage("Are you sure ?");
        builder.setTitle("choose organization");
        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                openOrdersActivity();
            }
        });

        builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context, "user canceled", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void openOrdersActivity(){
        Intent intent=new Intent(this, OredersActivity.class);
        startActivity(intent);
    }
}