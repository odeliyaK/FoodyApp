package com.foodyapp;
//commit try
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
 private Button reg_btn;
private  Button sign_btn;



 //commit try liad to origin

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView toolBarTitle=findViewById(R.id.toolbar_title);
        ImageView rightIcon=findViewById(R.id.menue);

        rightIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenu(v);
            }
        });



        reg_btn=(Button) findViewById(R.id.btn_register);
        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 openRegistrationActivity();
            }
        });

        sign_btn=(Button) findViewById(R.id.btn_signin);
        sign_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSignInActivity();
            }
        });
    }

    //Button registration on the MainActivity- pass to the registration page
    public void openRegistrationActivity(){
        Intent intent=new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }

    //opens the volunteer main activity
    public void openSignInActivity(){
        Intent intent=new Intent(this, SignInActivity.class);
        startActivity(intent);
    }

    private void showMenu(View v){
        PopupMenu popupMenu=new PopupMenu(MainActivity.this, v);
        popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId()== R.id.sign){
                    openSignInActivity();
                }
                else if (item.getItemId()== R.id.register){
                    openRegistrationActivity();
                }
                return true;
            }
        });
        popupMenu.show();
    }
}


