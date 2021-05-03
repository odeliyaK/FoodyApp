package com.foodyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SignInActivity extends AppCompatActivity {

    Button signIn_btn;
    EditText email;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        email=(EditText)findViewById(R.id.emailsignIn);
        password=(EditText)findViewById(R.id.passwordSignIn);
        signIn_btn=(Button)findViewById(R.id.sign_btn);
        signIn_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if the email is of a volunteer
                if (TextUtils.equals(email.getText(), "volunteer@gmail.com")){
                    if (TextUtils.equals(password.getText(), "volunteer")) {
                        openVolunteerActivity();
                    }else {
                        password.setError("Incorrect password");
                        password.requestFocus();
                    }
                }
                else if(TextUtils.equals(email.getText(), "latet@gmail.com")){
                    if (TextUtils.equals(password.getText(), "latet")) {
                        openOrgActivity();
                    }else {
                        password.setError("Incorrect password");
                        password.requestFocus();
                    }
                }
                else if (TextUtils.isEmpty(password.getText())){
                    password.setError("Password is empty");
                    password.requestFocus();
                } else {
                    email.setError("Email is not exists");
                    email.requestFocus();
                }
            }
        });



    }

    public void openVolunteerActivity(){
        Intent intent=new Intent(this, VolunteerMainActivity.class);
        startActivity(intent);
    }

    public void openOrgActivity(){
        Intent intent=new Intent(this, HouseHoldListActivity.class);
        startActivity(intent);
    }
}