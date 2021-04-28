package com.foodyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class RegistrationActivity extends AppCompatActivity  {
      Button organization_btn;
      Button back;
      EditText email;
      EditText pass;
    EditText confirm_pass;
      Button reg_btn;
    EditText phone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);


        organization_btn=(Button)findViewById(R.id.present_organizations);

        organization_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openOrganizationsActivity();
            }
        });

        email=(EditText)findViewById(R.id.emailAddress);
        pass=(EditText)findViewById(R.id.password);
        confirm_pass=(EditText)findViewById(R.id.confirm_password);
        phone=(EditText)findViewById(R.id.phone);
        back=(Button)findViewById(R.id.back_btn);
        reg_btn=(Button)findViewById(R.id.registration_btn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity();
            }
        });

        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(email.getText())){
                    email.setError("Enter an email");
                    email.requestFocus();
                } else if (TextUtils.isEmpty(pass.getText())){
                    pass.setError("Enter an password");
                    pass.requestFocus();
                }else if (!TextUtils.equals(pass.getText(), confirm_pass.getText())){
                    pass.setError("password not equal");
                    pass.requestFocus();
                    confirm_pass.setError("password not equal");
                    confirm_pass.requestFocus();
                }
            }
        });

    }

    public void openOrganizationsActivity(){
        if (TextUtils.isEmpty(email.getText())){
            email.setError("Enter an email");
            email.requestFocus();
        } else if (TextUtils.isEmpty(pass.getText())){
            pass.setError("Enter an password");
            pass.requestFocus();
        }else if (!TextUtils.equals(pass.getText(), confirm_pass.getText())){
            pass.setError("password not equal");
            pass.requestFocus();
            confirm_pass.setError("password not equal");
            confirm_pass.requestFocus();
        }
        else {
            Intent intent=new Intent(this, OrganizationListActivity.class);
            startActivity(intent);
        }

    }

    public void openMainActivity(){
        Intent intent=new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}