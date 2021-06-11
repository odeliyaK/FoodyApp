package com.foodyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.foodyapp.order.OrderActivity;
import com.google.firebase.auth.FirebaseAuth;

public class VolunteerMainActivity extends AppCompatActivity implements organizationsAlertDialogFragmentListener {

    private FirebaseAuth mAuth;

    ListView volList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_main);
        mAuth = FirebaseAuth.getInstance();
        ImageView rightIcon=findViewById(R.id.menu);
        Button orders = (Button) findViewById(R.id.orders);
        Button inventory = (Button) findViewById(R.id.inventory);
        Button parcels = (Button) findViewById(R.id.parcels);
        Button history = (Button) findViewById(R.id.history);
        ImageButton logOut = (ImageButton)  findViewById(R.id.logOut1);

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intent=new Intent(VolunteerMainActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


        orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(VolunteerMainActivity.this, OrderActivity.class);
                startActivity(intent);
            }
        });

        inventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(VolunteerMainActivity.this, InventoryActivity.class);
                startActivity(intent);
            }
        });

        parcels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(VolunteerMainActivity.this, UsersActivity.class);
                startActivity(intent);
            }
        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(VolunteerMainActivity.this, HistoryActivity.class);
                startActivity(intent);
            }
        });



    }


    private void showCustomAlertDialog() {
        OganizationDialogFragment frag = new OganizationDialogFragment();
        Bundle args = new Bundle();
        args.putInt("title", 1);
        frag.setArguments(args);
        frag.show(getFragmentManager(), "dialog");
    }

    @Override
    public void onDialogPositiveClick(OganizationDialogFragment dialog) {
//        String res= dialog.getFirstNameField() + " " + dialog.getLastNameField() + " " + dialog.getIdField() ;
//        Toast.makeText(this, "onDialogPositiveClick " + res,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDialogNegativeClick(OganizationDialogFragment dialog) {
        Toast.makeText(this, "onDialogNegativeClick " ,Toast.LENGTH_SHORT).show();
    }
    private void openMainActivity(){
        Intent intent=new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void showMenu(View v){
        PopupMenu popupMenu=new PopupMenu(VolunteerMainActivity.this, v);
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