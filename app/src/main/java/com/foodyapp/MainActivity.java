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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
 private Button reg_btn;
private  Button sign_btn;


 //commit try liad to origin

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyInfoManager.getInstance().openDataBase(this);
        MyInfoManager.getInstance().checksInserts();
        MyInfoManager.getInstance().products();


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

    @Override
    protected void onResume() {
        MyInfoManager.getInstance().openDataBase(this);
        super.onResume();

    }

    protected void onPause() {
        MyInfoManager.getInstance().closeDataBase();
        super.onPause();
    }

    protected void onStart() {
        super.onStart();
        //FirebaseApp.initializeApp(this);
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {

            Intent intent = new Intent(this, VolunteerMainActivity.class);

            startActivity(intent);

        }

    }

}


