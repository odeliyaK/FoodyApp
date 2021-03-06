package com.foodyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.foodyapp.model.Volunteers;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignInActivity extends AppCompatActivity {

    Button signIn_btn;
    EditText email;
    EditText password;
    private FirebaseAuth mAuth;
    ProgressBar prog;
    TextView percent;
    RelativeLayout relativeLayout;
    private Handler handler = new Handler();
    Context context;
    int progress = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        mAuth = FirebaseAuth.getInstance();
        MyInfoManager.getInstance().openDataBase(this);

        email=(EditText)findViewById(R.id.emailsignIn);
        password=(EditText)findViewById(R.id.passwordSignIn);
        prog = (ProgressBar)findViewById(R.id.progress_bar);
        percent = (TextView)findViewById(R.id.percent);
        relativeLayout = findViewById(R.id.relative);
        hideProgressBar();

        signIn_btn=(Button)findViewById(R.id.sign_btn);
        signIn_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signIn(email.getText().toString(), password.getText().toString());

            }
        });

    }

    private void signIn(String email, String password) {
        if (!validateForm()) {
            return;
        }
        showProgressBar();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        boolean flag = true;
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            if(email.equals("latet@gmail.com")){
                                flag = false;
                            }

                            updateUI(user, flag);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(SignInActivity.this, task.getException().toString(),
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null, flag);
                        }

                        // [START_EXCLUDE]
                        hideProgressBar();
                        // [END_EXCLUDE]
                    }
                });
        // [END sign_in_with_email]
    }

    public void openVolunteerActivity(){
        Intent intent=new Intent(this, VolunteerMainActivity.class);
        startActivity(intent);
    }

    public void openOrgActivity(){
        Intent intent=new Intent(this, LatetMenu.class);
        startActivity(intent);
    }

    private void updateUI(FirebaseUser user, boolean flag) {
        hideProgressBar();
        if (user != null){
            if(flag){
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                DocumentReference docRef = db.collection("Volunteers").document(email.getText().toString());
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            DocumentSnapshot document = task.getResult();
                            if(document.exists()){
                                Intent intent = new Intent(SignInActivity.this, VolunteerMainActivity.class);
                                startActivity(intent);
                            }
                            else{
                                user.delete();
                                Toast.makeText(SignInActivity.this, "Your account has been deleted", Toast.LENGTH_LONG).show();
                            }
                        }
                        //means the organization removed this user.
                        else{
                            user.delete();
                            Toast.makeText(SignInActivity.this, "Your account has been deleted", Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
            //Intent intent = new Intent(this, CitiesActivity.class);
            else
                openOrgActivity();

        } else {

        }
    }

    protected void onPause() {
        MyInfoManager.getInstance().closeDataBase();
        super.onPause();
    }

    public boolean validateForm(){
        boolean valid = true;
        String email1 = email.getText().toString();
        if (TextUtils.isEmpty(email1)) {
            email.setError("Required.");
            valid = false;
        } else {
            email.setError(null);
        }

        String password1 = password.getText().toString();
        if (TextUtils.isEmpty(password1)) {
            password.setError("Required.");
            valid = false;
        } else {
            password.setError(null);
        }

        return valid;
    }

    public void onStop() {
        super.onStop();
    }

    protected void onStart() {
        super.onStart();
        //FirebaseApp.initializeApp(this);
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser != null){
            if(currentUser.getEmail().equals("latet@gmail.com"))
                updateUI(currentUser, false);
            else
                updateUI(currentUser, true);
        }
    }

    public void showProgressBar() {
        if (prog != null) {
            prog.setVisibility(View.VISIBLE);
            percent.setVisibility(View.VISIBLE);
            relativeLayout.setVisibility(View.VISIBLE);
        }
    }

    public void hideProgressBar() {
        if (prog != null) {
            prog.setVisibility(View.GONE);
            percent.setVisibility(View.GONE);
            relativeLayout.setVisibility(View.GONE);
        }
    }
}