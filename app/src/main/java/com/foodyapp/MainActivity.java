package com.foodyapp;
//commit try
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.foodyapp.model.Products;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
 private Button reg_btn;
private  Button sign_btn;
Context context;


 //commit try liad to origin

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyInfoManager.getInstance().openDataBase(this);
//        MyInfoManager.getInstance().checksInserts();
        MyInfoManager.getInstance().products();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        WriteBatch batch = FirebaseFirestore.getInstance().batch();
        ArrayList<Products> products = MyInfoManager.getInstance().allProducts();
        for(Products p : products){
            DocumentReference dr = db.collection("Products").document(p.getName());
            batch.set(dr, p);
        }
        batch.commit().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                Toast.makeText(MainActivity.this, "we did it",Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(MainActivity.this, task.getException().toString(), Toast.LENGTH_LONG).show();
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
        if(currentUser == null)
            updateUI(null, false);
        else if(currentUser.getEmail().equals("latet@gmail.com"))
            updateUI(currentUser, false);
        else
            updateUI(currentUser, true);
    }

    private void updateUI(FirebaseUser user, boolean flag) {
        if (user != null) {
            if (flag) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                DocumentReference docRef = db.collection("Volunteers").document(user.getEmail());
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            DocumentSnapshot document = task.getResult();
                            if(document.exists()){
                                Intent intent = new Intent(MainActivity.this, VolunteerMainActivity.class);
                                startActivity(intent);
                            }
                            else{
                                user.delete();
                                Toast.makeText(MainActivity.this, "Your account has been deleted", Toast.LENGTH_LONG).show();
                            }
                        }
                        //means the organization removed this user.
                        else{
                            user.delete();
                            Toast.makeText(MainActivity.this, "Your account has been deleted", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
            else{
                Intent intent = new Intent(this, LatetMenu.class);
                startActivity(intent);
            }


        }

    }

}


